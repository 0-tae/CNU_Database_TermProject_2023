package cnu2023.cnu_database_termproject_2023.total;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class PaymentAndRentalPerYearDto {
    String CAR;
    String RENTDATE;
    String TOTAL_Rentaled;
    String TOTAL_Payment;
}