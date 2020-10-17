package ac.kr.smu.lookie.socialworker.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {
    public SimpleMailMessage createMessage(String to, String title, String text);
    public String sendSimpleMessage(String to);
}
