package cnu2023.cnu_database_termproject_2023.previousrental;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;



@Table(name ="PREVIOUSRENTAL")
@Entity
@Getter
@ToString
@NoArgsConstructor
@IdClass(PreviousRentalPK.class)
public class PreviousRental {
    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

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

    @Builder
    public PreviousRental(String licensePlateNo, LocalDate dateRented, LocalDate dateReturned, Integer payment, Customer customer) {
        this.licensePlateNo = licensePlateNo;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
        this.payment = payment;
        this.customer = customer;
    }
}

