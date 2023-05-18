package cnu2023.cnu_database_termproject_2023.previousrental;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@ToString
public class PreviousRentalDto {
    private String licensePlateNo;
    private LocalDate dateRented;
    private LocalDate dateReturned;
    private Integer payment;
    private Customer customer;

    @Builder
    public PreviousRentalDto(String licensePlateNo, LocalDate dateRented, LocalDate dateReturned, Integer payment, Customer customer) {
        this.licensePlateNo = licensePlateNo;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
        this.payment = payment;
        this.customer = customer;
    }

    PreviousRental toEntity(){
        return PreviousRental.builder().
                licensePlateNo(licensePlateNo).
                dateRented(dateRented).
                dateReturned(dateReturned).
                payment(payment).
                customer(customer).build();
    }
}
