package cnu2023.cnu_database_termproject_2023.reserve;

import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalPK;
import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservePK implements Serializable {
    private RentCar rentCar;

    private LocalDate startDate;
    @Override
    public int hashCode() {
        return rentCar.hashCode()* startDate.getMonthValue()* startDate.getDayOfMonth();
    }

    @Override
    public boolean equals(Object obj) {
        if(((ReservePK)obj).rentCar.equals(this.rentCar)){
            return ((ReservePK) obj).startDate.isEqual(this.startDate);
        }
        return false;
    }
}
