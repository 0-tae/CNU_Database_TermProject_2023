package reserve;

import customer.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final CustomerService customerService;

    public ReserveService(ReserveRepository reserveRepository, CustomerService customerService) {
        this.reserveRepository = reserveRepository;
        this.customerService = customerService;
    }

    public Reserve findReserveByLicence(String licensePlateNo,LocalDateTime startDate){
        return reserveRepository.findById(new ReservePK(licensePlateNo,startDate)).orElse(null);
    }

    public List<Reserve> findReserveAll(){
        return reserveRepository.findAll();
    }
    public List<Reserve> findReserveAllByCno(String cno){
        return reserveRepository.findAll().stream().
                filter(reserve->reserve.getCustomer().getCno().equals(cno)).
                collect(Collectors.toList());
    }



    public boolean isReserveTimeConflict(Reserve existReservation,LocalDateTime inputDateTime){
        if(existReservation.getStartDate()==null || existReservation.getEndDate()==null)
            return false;

        return existReservation.getStartDate().isAfter(inputDateTime) &&
                existReservation.getEndDate().isBefore(inputDateTime);
    }


    public boolean cancelReserve(String licensePLateNo, LocalDateTime dateTime){
        try{
            reserveRepository.deleteById(new ReservePK(licensePLateNo, dateTime));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Reserve saveReserve(ReserveDto dto){
        Reserve reserve=dto.toEntity(customerService.findCustomer(dto.getCno()));
        return reserveRepository.save(reserve);
    }

}
