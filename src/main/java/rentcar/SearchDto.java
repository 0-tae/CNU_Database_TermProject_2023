package rentcar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    public String vehicleType;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
}
