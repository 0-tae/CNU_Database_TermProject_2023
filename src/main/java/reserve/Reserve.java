package reserve;

import carmodel.CarModel;
import customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVE")

public class Reserve {

    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

    @Id
    @Column(name = "STARTDATE")
    private LocalDateTime startDate;

    @Column(name = "ENDDATE")
    private LocalDateTime endDate;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY) // 헷갈려
    @JoinColumn(name = "CNO")
    private Customer cno;
}

