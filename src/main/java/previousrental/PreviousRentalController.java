package previousrental;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PreviousRentalController {

    private final PreviousRentalService previousRentalService;

    public PreviousRentalController(PreviousRentalService previousRentalService) {
        this.previousRentalService = previousRentalService;
    }

    @GetMapping("previous/readAll")
    public List<ResponseDtoForPreviousRental> readAll(@RequestParam String cno){ // 이전 대여 내역 출력(고객 기준)
        List<ResponseDtoForPreviousRental> response=previousRentalService.convertPreviousRentalToDto(cno);
        response.sort((o1, o2) -> o1.getDateRented().compareTo(o2.getDateRented()));
        return response;
    }
}
