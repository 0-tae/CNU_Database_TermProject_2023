package reserve;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservePK implements Serializable {
    private String licensePlateNo;
    private LocalDateTime startDate;

}
