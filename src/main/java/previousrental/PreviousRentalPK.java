package previousrental;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PreviousRentalPK implements Serializable {
    private String licensePlateNo;
    private LocalDateTime dateRented;
}
