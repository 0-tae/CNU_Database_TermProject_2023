package reserve;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReservePK implements Serializable {
    private String licensePlateNo;
    private LocalDateTime startDate;
}
