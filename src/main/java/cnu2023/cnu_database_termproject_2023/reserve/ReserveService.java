package cnu2023.cnu_database_termproject_2023.reserve;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import cnu2023.cnu_database_termproject_2023.customer.CustomerService;
import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import cnu2023.cnu_database_termproject_2023.rentcar.RentCarService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final CustomerService customerService;

    private final EntityManager entityManager;

    public ReserveService(ReserveRepository reserveRepository, CustomerService customerService, EntityManager entityManager) {
        this.reserveRepository = reserveRepository;
        this.customerService = customerService;
        this.entityManager = entityManager;
    }

    public Reserve findReserveByLicence(String licensePlateNo,LocalDate startDate){
        RentCar rentCar=entityManager.find(RentCar.class,licensePlateNo);
        return reserveRepository.findById(new ReservePK(rentCar,startDate)).orElse(null);
    }

    public List<Reserve> findReserveAll(){
        return reserveRepository.findAll();
    }
    public List<Reserve> findReserveAllByCno(String cno){
        return findReserveAll().stream().
                filter(reserve->reserve.getCustomer().getCno().equals(cno)).
                toList();
    }

    public boolean isReserveTimeConflict(Reserve existReservation,LocalDate inputDateTime){
        if(existReservation.getStartDate()==null || existReservation.getEndDate()==null)
            return false;

        return (existReservation.getStartDate().isAfter(inputDateTime) &&
                existReservation.getEndDate().isBefore(inputDateTime))||
                (existReservation.getStartDate().isEqual(inputDateTime)
                || existReservation.getEndDate().isEqual(inputDateTime));
    }

    public List<Reserve> getReservationList(LocalDate today){
        return this.findReserveAll().stream().
                filter(reserve -> reserve.getStartDate().isEqual(today)).toList();
    }

    public boolean cancelReserve(String licensePLateNo, LocalDate dateTime){
        try{
            String license=licensePLateNo.split("/")[0];
            RentCar rentCar=entityManager.find(RentCar.class,license);

            reserveRepository.deleteById(new ReservePK(rentCar, dateTime));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Reserve saveReserve(ReserveDto dto){
        RentCar rentCar=entityManager.find(RentCar.class,dto.getLicensePlateNo());
        Customer customer=customerService.findCustomer(dto.getCno());

        Reserve reserve=dto.toEntity(rentCar,customer);

        List<Reserve> foundReserveList=findReserveAllByCno(dto.getCno());

        boolean reservationConflict=foundReserveList.stream().anyMatch(reservation->
                        isReserveTimeConflict(reservation, reserve.getStartDate()) ||
                        isReserveTimeConflict(reservation, reserve.getEndDate()) ||
                                reservation.getCustomer().getIs_renting()==1);

        if(reservationConflict) return null;

        return reserveRepository.save(reserve);
    }

}
