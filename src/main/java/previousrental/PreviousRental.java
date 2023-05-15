package previousrental;

import carmodel.CarModel;
import customer.Customer;
import jakarta.persistence.*;
import rentcar.RentCar;

import java.time.LocalDateTime;


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
    private Customer cno;
}

