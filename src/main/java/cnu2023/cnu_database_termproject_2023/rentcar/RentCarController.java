package cnu2023.cnu_database_termproject_2023.rentcar;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@Slf4j
public class RentCarController {
    private final RentCarService rentCarService;

    public RentCarController(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @GetMapping("/rent/readAll") // 검증완료
    private List<ResponseDtoForRentCar> readAll(HttpSession session){ // 대여내역 검색
        List<RentCar> rentalList = rentCarService.findRentCarsAllByCno((String) session.getAttribute("cno"));

        return  rentalList.stream().map(rentcar -> ResponseDtoForRentCar.builder()
                .licensePlateNum(rentcar.getLicensePlateNo())
                .modelName(rentcar.getCarModel().getModelName())
                .dateRented(rentcar.getDateRented())
                .dateDue(rentcar.getDateDue())
                .payment(rentCarService.paymentCalculation
                        (rentcar,(int)rentcar.getDateRented().until(rentcar.getDateDue(), ChronoUnit.DAYS)))
                .build()).sorted((o1, o2) -> o1.getDateRented().compareTo(o2.getDateRented())).toList();
    }

    @GetMapping("/rent/search")
    private List<RentCar> search(@RequestBody SearchDto searchDto){ // 필터, 대여기간 설정 후 검색
        return rentCarService.searchFullFilteredRentCars(searchDto);
    }
    @PostMapping("/rent/return") // 검증 완료
    private String delete(@RequestParam String licensePlateNum, HttpSession session){ // 반납
        if(!rentCarService.carReturn(licensePlateNum,(String)session.getAttribute("cno")))
            return licensePlateNum+" : error in returnCar";
        else
            return licensePlateNum + " is deleted at rentalList";
    }
}
