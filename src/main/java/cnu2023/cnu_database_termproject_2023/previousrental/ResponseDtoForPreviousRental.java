package cnu2023.cnu_database_termproject_2023.previousrental;

import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class ResponseDtoForPreviousRental {
    RentCar rentCar;
    LocalDate dateRented;
    LocalDate dateReturned;
    Integer payment;
}
