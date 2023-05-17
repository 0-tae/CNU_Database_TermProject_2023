package cnu2023.cnu_database_termproject_2023.options;

import cnu2023.cnu_database_termproject_2023.reserve.ReservePK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionsPK implements Serializable {
    private String licensePlateNo;
    private String optionName;

    @Override
    public int hashCode() {
        return licensePlateNo.hashCode()*optionName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(((OptionsPK)obj).licensePlateNo.compareTo(this.licensePlateNo)==0){
            return ((OptionsPK) obj).optionName.compareTo(this.optionName)==0;
        }
        return false;
    }
}
