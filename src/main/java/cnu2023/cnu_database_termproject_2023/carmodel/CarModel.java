package cnu2023.cnu_database_termproject_2023.carmodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Locale;

@Table(name = "CARMODEL")
@Entity
@Getter
@ToString
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



