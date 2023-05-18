package cnu2023.cnu_database_termproject_2023.reserve;

import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservePK implements Serializable {
    private String licensePlateNo;
    private LocalDate startDate;
    @Override
    public int hashCode() {
        return licensePlateNo.hashCode()* startDate.getMonthValue()* startDate.getDayOfMonth();
    }

    @Override
    public boolean equals(Object obj) {
        if(((ReservePK)obj).licensePlateNo.compareTo(this.licensePlateNo)==0){
            return ((ReservePK) obj).startDate.isEqual(this.startDate);
        }
        return false;
    }
}
