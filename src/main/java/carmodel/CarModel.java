package carmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Locale;

@Table(name = "CARMODEL")
@Entity
@Getter
public class CarModel {
    @Id
    @Column(name = "MODELNAME")
    private String modelName;

    @Column(name = "VEHICLETYPE")
    private String vehicleType;

    @Column(name = "RENTRATEPERDAY")
    private Integer rentRatePerDay;

    @Column(name = "FUEL")
    private String fuel;

    @Column(name = "NUMBEROFSEATS")
    private Integer numberOfSeats;
}
