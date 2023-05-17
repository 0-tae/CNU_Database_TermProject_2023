package cnu2023.cnu_database_termproject_2023.customer;


import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/main/login") // 검증완료
    private String login(@RequestBody String cno, @RequestBody String passwd, HttpSession session){
        Customer customer=customerService.findCustomer(cno);

        if(customer==null) return "notfound"; // 못 찾으면 not found
        String getCno=customer.getCno();
        session.setAttribute("cno",getCno);

        return getCno+", session id: "+session.getId();
    } // 로그인 하면 유저 아이디를 얻어올 수 있다. session-check을 통해서만 아이디를 얻어오자.
    @PostMapping("main/register") // 검증완료
    private String register(@RequestParam Map<String,String> params) {
        Customer customer=customerService.saveCustomer(params);
        return "Success: " + customer.getName();
    }

    @PostMapping("session_check") // 검증완료
    private String session_check(HttpSession session){
        return "session: "+session.getAttribute("cno")+", "+session.getId();
    }

}
