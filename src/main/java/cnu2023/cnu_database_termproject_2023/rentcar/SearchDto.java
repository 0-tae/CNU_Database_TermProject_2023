package cnu2023.cnu_database_termproject_2023.rentcar;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@ToString
public class SearchDto {

    public String vehicleType;
    public LocalDate startDate;
    public LocalDate endDate;

    @Builder
    public SearchDto(String vehicleType, LocalDate startDate, LocalDate endDate) {
        this.vehicleType = vehicleType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
