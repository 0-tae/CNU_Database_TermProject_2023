package rentcar;


import jakarta.servlet.http.HttpSession;
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
    private List<RentCar> search(@RequestBody SearchDto searchDto){
        return rentCarService.searchFullFilteredRentCars(searchDto);
    }

    @PostMapping("rent/return")
    private String delete(@RequestParam String licensePlateNum){
        if(!rentCarService.carReturn(licensePlateNum))
            return licensePlateNum+" : error in returnCar";
        else
            return licensePlateNum + " is deleted at rentalList";
    }
}
