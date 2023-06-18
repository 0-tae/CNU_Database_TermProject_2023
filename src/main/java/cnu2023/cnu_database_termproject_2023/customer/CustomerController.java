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

    @PostMapping("/main/login") // 검증완료
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
    } // 로그인 하면 유저 아이디를 얻어올 수 있다. session-check을 통해서만 아이디를 얻어오자.
    @PostMapping("main/register") // 검증완료
    private String register(@RequestParam Map<String,String> params) {
        Customer customer=customerService.saveCustomer(params);
        return "Success: " + customer.getName();
    }

    @PostMapping("session_check") // 검증완료
    private CustomerSessionDto session_check(HttpSession session){
        return  CustomerSessionDto.builder().
                cno((String)session.getAttribute("cno")).
                sessionId(session.getId()).build();
    }

    @PostMapping("/session_destroy")
    private String session_destroy(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "session_destroyed";
    }
}
