package cnu2023.cnu_database_termproject_2023.previousrental;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PreviousRentalController {

    private final PreviousRentalService previousRentalService;

    public PreviousRentalController(PreviousRentalService previousRentalService) {
        this.previousRentalService = previousRentalService;
    }

    @GetMapping("/previous/readAll") // 검증완료
    public List<ResponseDtoForPreviousRental> readAll(HttpSession session){ // 이전 대여 내역 출력(고객 기준)
        return previousRentalService.convertPreviousRentalToDto((String) session.getAttribute("cno"));
    }
}
