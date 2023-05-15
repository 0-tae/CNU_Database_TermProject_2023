package rentcar;

import carmodel.CarModel;
import customer.Customer;
import jakarta.persistence.*;
import previousrental.PreviousRentalPK;

import java.time.LocalDateTime;

@Entity
@Table(name = "RENTCAR")
public class RentCar {

    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

    @Column(name = "DATEDUE")
    private LocalDateTime dateDue;

    @Column(name = "DATEERETURNED")
    private LocalDateTime dateReturned;

    @ManyToOne(targetEntity = CarModel.class, fetch = FetchType.LAZY) // 헷갈려
    @JoinColumn(name = "MODELNAME")
    private CarModel modelName;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY) // 헷갈려
    @JoinColumn(name = "CNO")
    private Customer cno;
}