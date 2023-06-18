package cnu2023.cnu_database_termproject_2023.customer;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomer(String username){ // 유저 아이디를 통해 고객 튜플 가져옴
        return customerRepository.findById(username).orElse(null);
    }

    public Customer saveCustomer(Map<String,String> params){ // 회원 가입 정보를 기반으로 고객 정보 저장
        Customer customer=Customer.builder().cno(params.get("cno"))
                .name(params.get("name"))
                .passwd(params.get("passwd"))
                .email(params.get("email")).build();// customer 튜플 생성
        return customerRepository.save(customer); // 저장
    }


}
