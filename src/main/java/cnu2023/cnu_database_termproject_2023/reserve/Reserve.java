package cnu2023.cnu_database_termproject_2023.reserve;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalPK;
import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "RESERVE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ReservePK.class)
@ToString
public class Reserve {

    @Id
    @ManyToOne(targetEntity = RentCar.class, fetch = FetchType.EAGER) // 헷갈려
    @JoinColumn(name = "LICENSEPLATENO")
    private RentCar rentCar;

    @Id
    @Column(name = "STARTDATE")
    private LocalDate startDate;

    @Column(name = "RESERVEDATE")
    private LocalDate reserveDate;

    @Column(name = "ENDDATE")
    private LocalDate endDate;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER) // 헷갈려
    @JoinColumn(name = "CNO")
    private Customer customer;
}

