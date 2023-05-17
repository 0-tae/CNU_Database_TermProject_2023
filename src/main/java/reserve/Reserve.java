package reserve;

import customer.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVE")
@Getter
@NoArgsConstructor
public class Reserve {
    @Builder
    public Reserve(String licensePlateNo, LocalDateTime startDate, LocalDateTime reserveDate, LocalDateTime endDate, Customer cno) {
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
    private LocalDateTime startDate;

    @Column(name = "RESERVEDATE")
    private LocalDateTime reserveDate;

    @Column(name = "ENDDATE")
    private LocalDateTime endDate;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY) // 헷갈려
    @JoinColumn(name = "CNO")
    private Customer customer;
}

