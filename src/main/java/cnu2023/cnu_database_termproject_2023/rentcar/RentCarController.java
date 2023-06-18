package cnu2023.cnu_database_termproject_2023.rentcar;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    private List<ResponseDtoForRentalInfo> readAll(HttpSession session){ // 대여내역 검색
        List<RentCar> rentalList = rentCarService.findRentCarsAllByCno((String) session.getAttribute("cno"));
        log.info("{}",rentalList);
        return  rentalList.stream().map(rentcar -> ResponseDtoForRentalInfo.builder()
                .licensePlateNo(rentcar.getLicensePlateNo())
                .modelName(rentcar.getCarModel().getModelName())
                .dateRented(rentcar.getDateRented())
                .dateDue(rentcar.getDateDue())
                .expectedPayment(rentCarService.paymentCalculation
                        (rentcar,(int)rentcar.getDateRented().until(rentcar.getDateDue(), ChronoUnit.DAYS)))
                .build()).sorted((o1, o2) -> o1.getDateRented().compareTo(o2.getDateRented())).toList();
    }

    @PostMapping("/rent/save")
    @Scheduled(fixedDelay = 10000)
//    @Scheduled(cron = "0 0 0 * * *")
    private void rent() {
        log.info("Start Rent: "+LocalDate.now());
        rentCarService.switchReservationToRent(LocalDate.now());
    }

    @GetMapping("/rent/search")
    private List<RentCar> search(@RequestParam String vehicleType,
                                 @RequestParam LocalDate startDate,
                                 @RequestParam LocalDate endDate){ // 필터, 대여기간 설정 후 검색
        SearchDto dto=SearchDto.builder().startDate(startDate)
                                        .endDate(endDate)
                                        .vehicleType(vehicleType).build();

        return rentCarService.searchFullFilteredRentCars(dto);
    }
    @PostMapping("/rent/return") // 검증 완료
    private String delete(@RequestBody String licensePlateNo, HttpSession session){ // 반납
        String[] license=licensePlateNo.split("%");
        log.info("My name {}",licensePlateNo);

        if(!rentCarService.carReturn(license[0],(String)session.getAttribute("cno")))
            return license[0]+" : error in returnCar";
        else
            return license[0] + " is deleted at rentalList";
    }
}
