package cnu2023.cnu_database_termproject_2023.rentcar;

import cnu2023.cnu_database_termproject_2023.carmodel.CarModel;
import cnu2023.cnu_database_termproject_2023.customer.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
    
    private LocalDate dateDue;

    @Column(name = "DATERENTED")
    
    private LocalDate dateRented;

    @ManyToOne(targetEntity = CarModel.class, fetch = FetchType.EAGER) // 헷갈려
    @JoinColumn(name = "MODELNAME")
    private CarModel carModel;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER) // 헷갈려
    @JoinColumn(name = "CNO")
    private Customer customer;
}
