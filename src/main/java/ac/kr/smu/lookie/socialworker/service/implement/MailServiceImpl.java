package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final String title = "social worker 임시 비밀번호 안내";
    private final String text = "임시 비밀번호 입니다.<br><br> 임시 비밀번호 : ";

    @Override
    public SimpleMailMessage createMessage(String to, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(title);
        message.setText(text);
        return message;
    }

    @Override
    public String sendSimpleMessage(String to) {
        String password = createNewPassword();
        SimpleMailMessage message = createMessage(to,title, text+password);
        try{
            mailSender.send(message);
            return password;
        }catch (MailException e){
            e.printStackTrace();
        }
        return null;
    }

    public String createNewPassword(){
        StringBuffer buffer = new StringBuffer();
        Random r = new Random();
        for(int i=0;i<8;i++){
            int rIndex = r.nextInt(3);
            switch (rIndex){
                case 0:
                    buffer.append((char)((int)(r.nextInt(26))+97));
                    break;
                case 1:
                    buffer.append((char)((int)(r.nextInt(26))+65));
                    break;
                case 2:
                    buffer.append(r.nextInt(10));
                    break;
            }
        }
        return buffer.toString();
    }
}
