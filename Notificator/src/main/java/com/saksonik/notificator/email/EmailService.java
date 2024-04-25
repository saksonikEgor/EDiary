package com.saksonik.notificator.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.sender.email}")
    private String emailSender;
    private final JavaMailSender mailSender;

    public void sendMassage(String receiver, String name, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSender);
        helper.setTo(receiver);
        helper.setSubject(name);
        helper.setText(text);
        helper.setText("""
                <div style=\\"background-color: #f0f0f0; padding: 20px;\\">
                    <h1 style=\\"color: #333;\\">Уведомление от EDiary</h1>
                    <p style=\\"color: #666; font-size: 16px;\\">"""
                + text + """
                    </p>
                    <hr>
                    <p style=\\"color: #666; font-size: 16px;\\">Благодарим вас за использование нашего сервиса!</p>
                    <p style=\\"color: #666; font-size: 16px;\\">С уважением,<br>Команда EDiary</p>
                </div>""", true);

        mailSender.send(message);
    }
}
