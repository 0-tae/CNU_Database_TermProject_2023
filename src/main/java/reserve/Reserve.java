package reserve;

import carmodel.CarModel;
import customer.Customer;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVE")
public class Reserve {
    @Builder
    public Reserve(String licensePlateNo, LocalDateTime startDate, LocalDateTime reserveDate, LocalDateTime endDate, Customer cno) {
        this.licensePlateNo = licensePlateNo;
        this.startDate = startDate;
        this.reserveDate = reserveDate;
        this.endDate = endDate;
        this.cno = cno;
    }

    public Reserve() {}

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
    private Customer cno;

    public String getLicensePlateNo() {
        return licensePlateNo;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Customer getCno() {
        return cno;
    }
}

