package customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomer(String username){
        return customerRepository.findById(username).orElse(null);
    }

    public Customer saveCustomer(CustomerSaveDto customerSaveDto){
        Customer customer=customerSaveDto.toEntity();
        return customerRepository.save(customer);
    }


}
