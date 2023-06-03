package cnu2023.cnu_database_termproject_2023.rentcar;

import cnu2023.cnu_database_termproject_2023.carmodel.CarModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@ToString
@AllArgsConstructor
@Builder
public class ResponseDtoForRentalInfo {
    String modelName;
    String licensePlateNo;
    LocalDate dateRented;
    LocalDate dateDue;
    CarModel carModel;
    Integer expectedPayment;
}
