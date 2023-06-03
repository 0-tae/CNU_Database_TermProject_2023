package cnu2023.cnu_database_termproject_2023.previousrental;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@ToString
@AllArgsConstructor
@Builder
public class PreviousRentalDto {
    private RentCar rentCar;
    private LocalDate dateRented;
    private LocalDate dateReturned;
    private Integer payment;
    private Customer customer;
    PreviousRental toEntity(){
        return PreviousRental.builder().
                rentCar(rentCar).
                dateRented(dateRented).
                dateReturned(dateReturned).
                payment(payment).
                customer(customer).build();
    }
}
