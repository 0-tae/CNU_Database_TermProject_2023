package cnu2023.cnu_database_termproject_2023.reserve;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalPK;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "RESERVE")
@Getter
@NoArgsConstructor
@IdClass(ReservePK.class)
@ToString
public class Reserve {
    @Builder
    public Reserve(String licensePlateNo, LocalDate startDate, LocalDate reserveDate, LocalDate endDate, Customer cno) {
        this.licensePlateNo = licensePlateNo;
        this.startDate = startDate;
        this.reserveDate = reserveDate;
        this.endDate = endDate;
        this.customer = cno;
    }

    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

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

