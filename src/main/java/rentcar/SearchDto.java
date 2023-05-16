package rentcar;

import java.time.LocalDateTime;

public class SearchDto {
    public String vehicleType;
    public LocalDateTime startDate;
    public LocalDateTime endDate;

    public SearchDto(String vehicleType, LocalDateTime startDate, LocalDateTime endDate) {
        this.vehicleType = vehicleType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
