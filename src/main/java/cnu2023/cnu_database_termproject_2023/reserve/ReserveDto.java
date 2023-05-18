package cnu2023.cnu_database_termproject_2023.reserve;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class ReserveDto {
    private String licensePlateNo;
    private LocalDate startDate;
    private LocalDate endDate;
    private String cno; // 요청 받을 당시, String 형태의 cno로 들어온다.

    public ReserveDto(String licensePlateNo, LocalDate startDate, LocalDate endDate, String cno) {
        this.licensePlateNo = licensePlateNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cno = cno;
    }

    Reserve toEntity(Customer cno){
        return Reserve.builder().
                licensePlateNo(licensePlateNo).
                startDate(startDate).
                endDate(endDate).
                reserveDate(LocalDate.now()).
                cno(cno).build();
    }
}
