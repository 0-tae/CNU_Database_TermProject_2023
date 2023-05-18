package cnu2023.cnu_database_termproject_2023.rentcar;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDate;


@Getter
@ToString
public class ResponseDtoForRentCar {
    String modelName;
    String licensePlateNum;
    LocalDate dateRented;
    LocalDate dateDue;
    Integer payment;

    @Builder
    public ResponseDtoForRentCar(String modelName, String licensePlateNum, LocalDate dateRented, LocalDate dateDue, Integer payment) {
        this.modelName = modelName;
        this.licensePlateNum = licensePlateNum;
        this.dateRented = dateRented;
        this.dateDue = dateDue;
        this.payment = payment;
    }
}
