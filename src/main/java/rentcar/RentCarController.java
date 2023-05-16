package rentcar;


import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentCarController {
    private final RentCarService rentCarService;

    public RentCarController(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @GetMapping("rent/readAll")
    private List<RentCar> readAll(HttpSession session){ // load All by cno
        return rentCarService.findRentCarsAllByCno((String)session.getAttribute("cno"));
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
