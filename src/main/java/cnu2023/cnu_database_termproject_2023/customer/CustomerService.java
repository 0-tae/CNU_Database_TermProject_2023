package cnu2023.cnu_database_termproject_2023.customer;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomer(String username){
        return customerRepository.findById(username).orElse(null);
    }

    public Customer saveCustomer(Map<String,String> params){
        CustomerSaveDto customerSaveDto=
                        CustomerSaveDto.builder().cno(params.get("cno"))
                        .name(params.get("name"))
                        .passwd(params.get("passwd"))
                        .email(params.get("email")).build();

        Customer customer=customerSaveDto.toEntity();
        return customerRepository.save(customer);
    }


}
