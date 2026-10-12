
package com.example.backend.service;

import com.example.backend.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailVerificationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    private final SecureRandom random = new SecureRandom();

    private final Map<String, VerificationData> verificationData =
            new ConcurrentHashMap<>();

    private final Set<String> verifiedEmails =
            ConcurrentHashMap.newKeySet();

    private static final long EXPIRE_TIME = 5 * 60 * 1000L;
    private static final long RESEND_WAIT = 60 * 1000L;
    private static final int MAX_ATTEMPTS = 5;

    public EmailVerificationService(
            JavaMailSender mailSender,
            UserRepository userRepository
    ) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void sendCode(String email) {
        email = normalizeEmail(email);
        validateEmail(email);

        if (userRepository.findByUserId(email).isPresent()) {
            throw new IllegalArgumentException(
                    "이미 가입된 이메일입니다."
            );
        }

        long now = System.currentTimeMillis();

        VerificationData previous = verificationData.get(email);

        if (previous != null
                && now - previous.sentAt < RESEND_WAIT) {
            throw new IllegalArgumentException(
                    "인증번호는 60초 후에 다시 요청할 수 있습니다."
            );
        }

        String code = String.format(
                "%06d",
                random.nextInt(1_000_000)
        );

        VerificationData data = new VerificationData(
                code,
                now,
                now
        );

        verificationData.put(email, data);
        verifiedEmails.remove(email);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[틈짓] 이메일 인증 요청");
            message.setText(
                    "안녕하세요, 틈짓 회원가입을 진심으로 환영합니다.\n\n"
                    + "이메일 인증번호는 [" + code + "]입니다.\n"
                    + "인증번호는 발송 시점부터 5분간 유효합니다.\n\n"
                    + "본인이 요청하지 않았다면 이 메일을 무시해 주세요."
            );

            mailSender.send(message);

        } catch (RuntimeException e) {
            verificationData.remove(email, data);
            throw e;
        }
    }

    public void verifyCode(String email, String code) {
        email = normalizeEmail(email);

        VerificationData data = verificationData.get(email);

        if (data == null) {
            throw new IllegalArgumentException(
                    "인증번호를 먼저 요청해 주세요."
            );
        }

        long now = System.currentTimeMillis();

        if (now - data.sentAt >= EXPIRE_TIME) {
            verificationData.remove(email, data);
            throw new IllegalArgumentException(
                    "인증번호가 만료되었습니다. 다시 요청해 주세요."
            );
        }

        if (data.attempts >= MAX_ATTEMPTS) {
            verificationData.remove(email, data);
            throw new IllegalArgumentException(
                    "인증번호 입력 횟수를 초과했습니다. 다시 요청해 주세요."
            );
        }

        data.attempts++;

        if (!data.code.equals(code)) {
            throw new IllegalArgumentException(
                    "인증번호가 올바르지 않습니다."
            );
        }

        verificationData.remove(email, data);
        verifiedEmails.add(email);
    }

    public boolean consumeVerifiedEmail(String email) {
        return verifiedEmails.remove(normalizeEmail(email));
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException(
                    "이메일을 입력해 주세요."
            );
        }

        return email.trim().toLowerCase(java.util.Locale.ROOT);
    }

    private void validateEmail(String email) {
        if (!email.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )) {
            throw new IllegalArgumentException(
                    "올바른 이메일 주소를 입력해 주세요."
            );
        }
    }

    private static class VerificationData {
        private final String code;
        private final long sentAt;
        private final long createdAt;
        private int attempts;

        private VerificationData(
                String code,
                long sentAt,
                long createdAt
        ) {
            this.code = code;
            this.sentAt = sentAt;
            this.createdAt = createdAt;
            this.attempts = 0;
        }
    }
}
