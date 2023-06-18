package cnu2023.cnu_database_termproject_2023.mail;


import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRental;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService{
    private final MailSender mailSender; // 메일 전송용 프레임워크 클래스

    public MailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(PreviousRental info){
            String content =
                    "아이디: "+ info.getCustomer().getName()+" \n"+
                    "반납한 렌트카: "+info.getRentCar().getLicensePlateNo()+", "+info.getRentCar().getCarModel().getModelName()+"\n"+
                    "대여기간: "+info.getDateRented()+" ~ "+info.getDateReturned()+"\n"+
                    "결제 금액: "+info.getPayment()+"\n";

            SimpleMailMessage message = new SimpleMailMessage(); // 메시지 인스턴스 생성

            message.setSubject(info.getCustomer().getName()+ "님, 결제 내역입니다.");
            message.setText(content);
            message.setFrom("choiyt3465@naver.com");
            message.setTo("0tae@o.cnu.ac.kr"); // info.getEmail();

            // 수신, 발신, 제목, 내용 세팅

            mailSender.send(message); // 메일 전송
    }
}
