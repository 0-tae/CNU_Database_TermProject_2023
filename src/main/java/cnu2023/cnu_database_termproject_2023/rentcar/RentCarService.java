package cnu2023.cnu_database_termproject_2023.rentcar;

import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalDto;
import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalService;
import cnu2023.cnu_database_termproject_2023.reserve.Reserve;
import cnu2023.cnu_database_termproject_2023.reserve.ReserveService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentCarService {

    private final RentCarRepository rentCarRepository;
    private final ReserveService reserveService;
    private final PreviousRentalService previousRentalService;
    private final EntityManager entityManager;


    public RentCarService(RentCarRepository rentCarRepository, ReserveService reserveService, PreviousRentalService previousRentalService, EntityManager entityManager) {
        this.rentCarRepository = rentCarRepository;
        this.reserveService = reserveService;
        this.previousRentalService = previousRentalService;
        this.entityManager = entityManager;
    }

    public List<RentCar> searchFullFilteredRentCars(SearchDto searchDto){
        List<RentCar> availableList=findAvailableRentCars(searchDto);
        List<RentCar> filteredList;

        filteredList=availableList.stream().filter(rentCar->{
            Reserve reserve=reserveService.findReserveByLicence(rentCar.getLicensePlateNo(),rentCar.getDateRented());
            return reserve==null || !(reserveService.isReserveTimeConflict(reserve,searchDto.startDate)
                    && reserveService.isReserveTimeConflict(reserve,searchDto.endDate));
        }).collect(Collectors.toList());

        return filteredList;
    }

    public List<RentCar> findAvailableRentCars(SearchDto searchDto){
        List<RentCar> entierList = findRentCarsAll();
        List<RentCar> filteredList;

        filteredList=entierList.stream().filter(rentCar->
                rentCar.getCarModel().getVehicleType().equals(searchDto.vehicleType)
                &&!(isRentalTimeConflict(rentCar,searchDto.startDate) && isRentalTimeConflict(rentCar,searchDto.endDate)))
                .collect(Collectors.toList());

        return filteredList;
    }

    public List<RentCar> findRentCarsAll(){
        return rentCarRepository.findAll();
    }

    public List<RentCar> findRentCarsAllByCno(String cno){
        return rentCarRepository.findAll().stream().filter(car->car.getCustomer()!=null).
                filter(car->car.getCustomer().getCno().equals(cno)).toList();
    }

    private boolean isRentalTimeConflict(RentCar existRental, LocalDateTime inputDateTime){
        if(existRental.getDateRented()==null || existRental.getDateDue()==null)
            return false; // 대여중이지 않은 것

        return existRental.getDateRented().isAfter(inputDateTime) &&
                existRental.getDateDue().isBefore(inputDateTime);
    }

    public int paymentCalculation(RentCar rentCar, int totalDay){
        return rentCar.getCarModel().getRentRatePerDay()*totalDay;
    }
    public PreviousRentalDto createPreviousRentalData(RentCar rentCar){
        LocalDateTime returnDate=LocalDateTime.now();
        int perDay=(int)rentCar.getDateRented().until(returnDate, ChronoUnit.DAYS);
        int payment=paymentCalculation(rentCar,perDay);

        return PreviousRentalDto.builder().
                licensePlateNo(rentCar.getLicensePlateNo()).
                dateRented(rentCar.getDateRented()).
                customer(rentCar.getCustomer()).
                dateReturned(returnDate).
                payment(perDay*payment).build();
    }
    @Transactional
    public boolean carReturn(String givenLicensePlateNum, String cno){
        RentCar rentCar = entityManager.find(RentCar.class, givenLicensePlateNum);

        if(rentCar==null || !rentCar.getCustomer().getCno().equals(cno)) return false;

        previousRentalService.save(createPreviousRentalData(rentCar));
        // 렌트카 정보 초기화 이전에 이전 대여정보를 생성

        rentCar.setCustomer(null);
        rentCar.setDateRented(null);
        rentCar.setDateDue(null);
        RentCar modified = entityManager.merge(rentCar);

        entityManager.persist(modified);

        return true;
    }

}