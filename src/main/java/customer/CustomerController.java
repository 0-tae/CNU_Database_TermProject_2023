package customer;


import jakarta.servlet.http.HttpSession;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("main/login")
    private String login(@RequestParam String cno, @RequestParam String passwd,HttpSession session){
        Customer customer=customerService.findCustomer(cno);

        if(customer==null) return "notfound"; // 못 찾으면 not found
        String username=customer.getName();
        session.setAttribute("cno",username);

        return username;
    } // 로그인 하면 유저 아이디를 얻어올 수 있다. session-check을 앞으로 통해서만 아이디를 얻어오자.
    @PostMapping("main/register")
    private String register(@RequestParam Map<String,String> params) {
        CustomerSaveDto customerSaveDto=CustomerSaveDto.builder().cno(params.get("cno"))
                        .name(params.get("name"))
                        .passwd(params.get("passwd"))
                        .email(params.get("email")).build();
        Customer customer=customerService.saveCustomer(customerSaveDto);

        return "Success: " + customer.getName();
    }

    @PostMapping("main/session-check")
    private String sessionCheck(HttpSession session){
        return (String)session.getAttribute("cno");
    } // session-check을 앞으로 통해서만 아이디를 얻어오자.
}
