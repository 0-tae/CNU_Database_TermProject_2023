package cnu2023.cnu_database_termproject_2023.previousrental;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ResponseDtoForPreviousRental {
    String modelName;
    String licensePlateNum;
    LocalDate dateRented;
    LocalDate dateReturned;
    Integer payment;
    @Builder
    public ResponseDtoForPreviousRental(String modelName, String licensePlateNum, LocalDate dateRented, LocalDate dateReturned, Integer payment) {
        this.modelName = modelName;
        this.licensePlateNum = licensePlateNum;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
        this.payment = payment;
    }
}
