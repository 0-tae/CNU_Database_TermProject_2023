package cnu2023.cnu_database_termproject_2023.previousrental;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreviousRentalPK implements Serializable {
    private String licensePlateNo;
    private LocalDate dateRented;

    @Override
    public int hashCode() {
        return licensePlateNo.hashCode()* getDateRented().getMonthValue()* dateRented.getDayOfMonth();
    }

    @Override
    public boolean equals(Object obj) {
        if(((PreviousRentalPK)obj).licensePlateNo.compareTo(this.licensePlateNo)==0){
            return ((PreviousRentalPK) obj).dateRented.isEqual(this.dateRented);
        }
        return false;
    }
}
