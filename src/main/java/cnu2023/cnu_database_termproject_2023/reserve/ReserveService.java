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
        return reserveRepository.findById(new ReservePK(rentCar,startDate)).orElse(null); // 차에 대한 예약 내역 불러오기
    }

    public List<Reserve> findReserveAll(){
        return reserveRepository.findAll(); // 모든 예약내역 불러오기
    }
    public List<Reserve> findReserveAllByCno(String cno){
        return findReserveAll().stream().
                filter(reserve->reserve.getCustomer().getCno().equals(cno)).toList(); // 한 고객의 예약 내역 불러오기
    }

    public boolean isReserveTimeConflict(Reserve existReservation,LocalDate inputDateTime){
        if(existReservation.getStartDate()==null || existReservation.getEndDate()==null)
            return false;

        return (existReservation.getStartDate().isAfter(inputDateTime) &&
                existReservation.getEndDate().isBefore(inputDateTime))||
                (existReservation.getStartDate().isEqual(inputDateTime)
                || existReservation.getEndDate().isEqual(inputDateTime)); // 시간 충돌 감지
    }

    public List<Reserve> getReservationList(LocalDate today){
        return this.findReserveAll().stream().
                filter(reserve -> reserve.getStartDate().isEqual(today)).toList(); // 오늘 날짜와 일치하는 예약정보 가져오기
    }

    public boolean cancelReserve(String licensePLateNo, LocalDate dateTime){
        try{
            String license=licensePLateNo.split("/")[0];
            RentCar rentCar=entityManager.find(RentCar.class,license); // licenseNum에 대해 예약 검색
            reserveRepository.deleteById(new ReservePK(rentCar, dateTime)); // 예약 Id 이용해서 예약 삭제
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Reserve saveReserve(ReserveDto dto){
        RentCar rentCar=entityManager.find(RentCar.class,dto.getLicensePlateNo()); // 대상 렌터카 엔티티
        Customer customer=customerService.findCustomer(dto.getCno()); // 대상 고객 엔티티

        Reserve reserve=dto.toEntity(rentCar,customer); // 예약 엔티티 생성

        List<Reserve> foundReserveList=findReserveAllByCno(dto.getCno()); // 고객의 예약 목록 생성

        boolean reservationConflict=foundReserveList.stream().anyMatch(reservation->
                        isReserveTimeConflict(reservation, reserve.getStartDate()) ||
                        isReserveTimeConflict(reservation, reserve.getEndDate()));
        // 예약 시간과 충돌이 나지 않거나, 대여 중인 렌트카가 없는지 확인

        if(reservationConflict) return null; // 있다면 null

        return reserveRepository.save(reserve); // 예약을 저장
    }

}
