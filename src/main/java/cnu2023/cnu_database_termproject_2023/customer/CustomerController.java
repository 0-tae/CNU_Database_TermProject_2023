package cnu2023.cnu_database_termproject_2023.customer;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.util.Map;

@RestController
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/main/login") // 로그인 요청 처리
    private String login(@RequestBody CustomerDto dto, HttpSession session){
        Customer customer=customerService.findCustomer(dto.cno);

        if(customer==null){
            log.info("what..is "+dto.cno);
            return "notfound"; // 못 찾으면 not found
        }
        String getCno=customer.getCno();
        session.setAttribute("cno",getCno);
        log.info((String)session.getAttribute("cno"));
        return getCno+", session id: "+session.getId();
    } // 로그인 하면 세션에 유저 아이디를 넣어 세션으로 유저 정보를 체크할 수 있다.

    @PostMapping("main/register") // 회원가입
    private String register(@RequestParam Map<String,String> params) {
        Customer customer=customerService.saveCustomer(params); // 데이터 베이스 저장
        return "Success: " + customer.getName();
    }

    @PostMapping("session_check") // 세션 체크
    private CustomerSessionDto session_check(HttpSession session){
        return  CustomerSessionDto.builder().
                cno((String)session.getAttribute("cno")).
                sessionId(session.getId()).build(); // 고객의 정보를 넘겨준다.
    }

    @PostMapping("/session_destroy")
    private String session_destroy(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // 로그아웃시, 세션 삭제
        }

        return "session_destroyed";
    }
}
