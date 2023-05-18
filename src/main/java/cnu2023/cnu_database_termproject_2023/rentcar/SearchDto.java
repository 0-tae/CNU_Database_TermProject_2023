package cnu2023.cnu_database_termproject_2023.rentcar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    public String vehicleType;
    public LocalDate startDate;
    public LocalDate endDate;
}
