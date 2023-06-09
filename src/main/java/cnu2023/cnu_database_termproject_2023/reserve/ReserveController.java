package cnu2023.cnu_database_termproject_2023.reserve;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ReserveController {
    private final ReserveService reserveService;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @GetMapping("/reserve/readAll") // 예약 목록 불러오기
    private List<Reserve> readAll(HttpSession session){ // load All by cno
        return reserveService.findReserveAllByCno((String)session.getAttribute("cno"));
    }

    @PostMapping("/reserve/save") // 예약 하기
    private Reserve save(@RequestBody ReserveDto reserveDto){
        return reserveService.saveReserve(reserveDto);
    }
    // 검색할 때 썼던 날짜를 사용하고, 프론트에서 각 검색결과 행에 예약 버튼 배치, 버튼 id는 cno
    // 예약이 완료되면 반환 json으로 프론트에 reserve 뷰 테이블 생성

    @PostMapping("/reserve/cancel") // 예약 취소
    private String cancel(@RequestBody ReserveDto reserveDto){
        if(!reserveService.cancelReserve(reserveDto.getLicensePlateNo(),reserveDto.getStartDate()))
            return "cancel error : "+reserveDto.getLicensePlateNo();
        else
            return reserveDto.getLicensePlateNo()+" is deleted";
    }
}
