package rentcar;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ResponseDtoForRentCar {
    String modelName;
    String licensePlateNum;
    LocalDateTime dateRented;
    LocalDateTime dateDue;
    Integer payment;

    @Builder
    public ResponseDtoForRentCar(String modelName, String licensePlateNum, LocalDateTime dateRented, LocalDateTime dateDue, Integer payment) {
        this.modelName = modelName;
        this.licensePlateNum = licensePlateNum;
        this.dateRented = dateRented;
        this.dateDue = dateDue;
        this.payment = payment;
    }

    public LocalDateTime getDateRented() {
        return dateRented;
    }
}
