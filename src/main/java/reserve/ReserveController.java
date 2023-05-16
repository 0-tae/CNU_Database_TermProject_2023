package reserve;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReserveController {
    private final ReserveService reserveService;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @GetMapping("reserve/read")
    private Reserve read(String cno){
        return reserveService.findReserveByCno(cno);
    }


    @PostMapping("reserve/save")
    private Reserve save(@RequestBody ReserveDto reserveDto){
        return reserveService.saveReserve(reserveDto);
    }
    // 검색할 때 썼던 날짜를 사용하고, 프론트에서 각 검색결과 행에 예약 버튼 배치, 버튼 id는 cno
    // 예약이 완료되면 반환 json으로 프론트에 reserve 뷰 테이블 생성

    @PostMapping("reserve/cancel")
    private String cancel(String licensePLateNo){
        if(!reserveService.cancelReserve(licensePLateNo)){
            return "cancel error : "+licensePLateNo;
        }
        return licensePLateNo+" is deleted";
    }
}
