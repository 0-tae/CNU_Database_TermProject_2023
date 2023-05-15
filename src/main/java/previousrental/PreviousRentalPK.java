package previousrental;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PreviousRentalPK implements Serializable {
    private String licensePlateNo;
    private LocalDateTime dateRented;
}
