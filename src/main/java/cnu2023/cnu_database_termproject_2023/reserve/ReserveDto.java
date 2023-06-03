package cnu2023.cnu_database_termproject_2023.reserve;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@ToString
@AllArgsConstructor
@Builder
public class ReserveDto {
    private String licensePlateNo;
    private LocalDate startDate;
    private LocalDate endDate;
    private String cno; // 요청 받을 당시, String 형태의 cno로 들어온다.

    Reserve toEntity(RentCar rentCar,Customer cno){
        return Reserve.builder().
                rentCar(rentCar).
                startDate(startDate).
                endDate(endDate).
                reserveDate(LocalDate.now()).
                customer(cno).build();
    }
}
