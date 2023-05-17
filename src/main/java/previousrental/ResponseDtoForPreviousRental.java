package previousrental;

import customer.Customer;
import lombok.Builder;

import java.time.LocalDateTime;

public class ResponseDtoForPreviousRental {
    String modelName;
    String licensePlateNum;
    LocalDateTime dateRented;
    LocalDateTime dateReturned;
    Integer payment;
    @Builder
    public ResponseDtoForPreviousRental(String modelName, String licensePlateNum, LocalDateTime dateRented, LocalDateTime dateReturned, Integer payment) {
        this.modelName = modelName;
        this.licensePlateNum = licensePlateNum;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
        this.payment = payment;
    }

    public LocalDateTime getDateRented() {
        return dateRented;
    }

    public LocalDateTime getDateReturned() {
        return dateReturned;
    }
}
