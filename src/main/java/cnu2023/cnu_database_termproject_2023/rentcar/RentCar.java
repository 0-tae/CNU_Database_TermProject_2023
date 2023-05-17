package cnu2023.cnu_database_termproject_2023.rentcar;

import cnu2023.cnu_database_termproject_2023.carmodel.CarModel;
import cnu2023.cnu_database_termproject_2023.customer.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "RENTCAR")
@Getter
@Setter
@ToString
public class RentCar {
    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

    @Column(name = "DATEDUE")
    private LocalDateTime dateDue;

    @Column(name = "DATERENTED")
    private LocalDateTime dateRented;

    @ManyToOne(targetEntity = CarModel.class, fetch = FetchType.EAGER) // 헷갈려
    @JoinColumn(name = "MODELNAME")
    private CarModel carModel;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER) // 헷갈려
    @JoinColumn(name = "CNO")
    private Customer customer;
}
