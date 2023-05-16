package rentcar;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentCarController {
    private final RentCarService rentCarService;

    public RentCarController(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    public String read(){
        return null;
    }

    public List<RentCar> search(@RequestBody SearchDto searchDto){
        return rentCarService.searchFullFilteredRentCars(searchDto);
    }
}
