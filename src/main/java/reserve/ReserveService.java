package reserve;

import customer.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final CustomerService customerService;

    public ReserveService(ReserveRepository reserveRepository, CustomerService customerService) {
        this.reserveRepository = reserveRepository;
        this.customerService = customerService;
    }

    public Reserve findReserveByCno(String cno){
        return reserveRepository.findById(cno).orElse(null);
    }

    public Reserve findReserveByLicence(String licensePlateNo){
        return reserveRepository.findById(licensePlateNo).orElse(null);
    }

    public List<Reserve> findReserveAll(){
        return reserveRepository.findAll();
    }



    public boolean isReserveTimeConflict(Reserve existReservation,LocalDateTime inputDateTime){
        if(existReservation.getStartDate()==null || existReservation.getEndDate()==null)
            return false;

        return existReservation.getStartDate().isAfter(inputDateTime) &&
                existReservation.getEndDate().isBefore(inputDateTime);
    }


    public boolean cancelReserve(String licensePLateNo){
        try{
            reserveRepository.deleteById(licensePLateNo);
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
