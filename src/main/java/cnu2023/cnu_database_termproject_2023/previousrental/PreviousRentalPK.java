package cnu2023.cnu_database_termproject_2023.previousrental;

import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreviousRentalPK implements Serializable {
    private RentCar rentCar;

    
    private LocalDate dateRented;

    @Override
    public int hashCode() {
        return rentCar.hashCode()* getDateRented().getMonthValue()* dateRented.getDayOfMonth();
    }

    @Override
    public boolean equals(Object obj) {
        if(((PreviousRentalPK)obj).rentCar.equals(this.rentCar)){
            return ((PreviousRentalPK) obj).dateRented.isEqual(this.dateRented);
        }
        return false;
    }
}
