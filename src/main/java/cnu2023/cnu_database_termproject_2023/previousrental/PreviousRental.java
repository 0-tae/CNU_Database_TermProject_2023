package cnu2023.cnu_database_termproject_2023.previousrental;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



@Table(name ="PREVIOUSRENTAL")
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PreviousRentalPK.class)
public class PreviousRental {
    @Id
    @ManyToOne(targetEntity = RentCar.class, fetch = FetchType.EAGER) // 헷갈려
    @JoinColumn(name = "LICENSEPLATENO")
    private RentCar rentCar;

    @Id
    @Column(name = "DATERENTED")
    private LocalDate dateRented;

    @Column(name = "DATERETURNED")
    private LocalDate dateReturned;

    @Column(name = "PAYMENT")
    private Integer payment;


    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER) // 왜 LAZY로 하면 안되는거지?
    @JoinColumn(name = "CNO")
    private Customer customer;
}

