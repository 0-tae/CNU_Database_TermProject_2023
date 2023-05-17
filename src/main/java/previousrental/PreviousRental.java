package previousrental;

import customer.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Table(name ="PREVIOUSRENTAL")
@Entity
@Getter
@NoArgsConstructor
public class PreviousRental {
    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

    @Id
    @Column(name = "DATEERENTED")
    private LocalDateTime dateRented;

    @Column(name = "DATEERETURNED")
    private LocalDateTime dateReturned;

    @Column(name = "PAYMENT")
    private Integer payment;


    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY) // 헷갈려
    @JoinColumn(name = "CNO")
    private Customer customer;

    @Builder
    public PreviousRental(String licensePlateNo, LocalDateTime dateRented, LocalDateTime dateReturned, Integer payment, Customer customer) {
        this.licensePlateNo = licensePlateNo;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
        this.payment = payment;
        this.customer = customer;
    }
}

