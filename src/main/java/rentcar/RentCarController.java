package rentcar;


import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RentCarController {
    private final RentCarService rentCarService;

    public RentCarController(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @GetMapping("rent/readAll")
    private List<ResponseDtoForRentCar> readAll(HttpSession session){ // 대여내역 검색
        List<RentCar> rentalList = rentCarService.findRentCarsAllByCno((String) session.getAttribute("cno"));
        List<ResponseDtoForRentCar> responses=
                rentalList.stream().map(rentcar -> ResponseDtoForRentCar.builder()
                                .licensePlateNum(rentcar.getLicensePlateNo())
                                .modelName(rentcar.getCarModel().getModelName())
                                .dateRented(rentcar.getDateRented())
                                .dateDue(rentcar.getDateDue())
                                .payment(rentCarService.paymentCalculation
                                        (rentcar,(int)rentcar.getDateRented().until(rentcar.getDateDue(), ChronoUnit.DAYS)))
                                .build()).toList();

        responses.sort((o1, o2) -> o1.getDateRented().compareTo(o2.getDateRented()));
        return responses;
    }

    @GetMapping("rent/search")
    private List<RentCar> search(@RequestBody SearchDto searchDto){ // 필터, 대여기간 설정 후 검색
        return rentCarService.searchFullFilteredRentCars(searchDto);
    }
    @PostMapping("rent/return")
    private String delete(@RequestParam String licensePlateNum){ // 반납
        if(!rentCarService.carReturn(licensePlateNum))
            return licensePlateNum+" : error in returnCar";
        else
            return licensePlateNum + " is deleted at rentalList";
    }
}
