package previousrental;

import customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PreviousRentalDto {
    private String licensePlateNo;
    private LocalDateTime dateRented;
    private LocalDateTime dateReturned;
    private Integer payment;
    private Customer customer;

    @Builder
    public PreviousRentalDto(String licensePlateNo, LocalDateTime dateRented, LocalDateTime dateReturned, Integer payment, Customer customer) {
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
