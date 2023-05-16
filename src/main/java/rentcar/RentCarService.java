package rentcar;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import previousrental.PreviousRental;
import reserve.Reserve;
import reserve.ReserveService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentCarService {

    private final RentCarRepository rentCarRepository;
    private final ReserveService reserveService;
    private final PreviousRental previousRental;
    private final EntityManager entityManager;


    public RentCarService(RentCarRepository rentCarRepository, ReserveService reserveService, PreviousRental previousRental, EntityManager entityManager) {
        this.rentCarRepository = rentCarRepository;
        this.reserveService = reserveService;
        this.previousRental = previousRental;
        this.entityManager = entityManager;
    }

    public List<RentCar> searchFullFilteredRentCars(SearchDto searchDto){
        List<RentCar> availableList=findAvailableRentCars(searchDto);
        List<RentCar> filteredList;

        filteredList=availableList.stream().filter(rentCar->{
            Reserve reserve=reserveService.findReserveByLicence(rentCar.getLicensePlateNo());
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
        return rentCarRepository.findAll().stream().
                filter(car->car.getCno().getCno().equals(cno)).
                collect(Collectors.toList());
    }

    private boolean isRentalTimeConflict(RentCar existRental, LocalDateTime inputDateTime){
        if(existRental.getDateRented()==null || existRental.getDateDue()==null)
            return false; // 대여중이지 않은 것

        return existRental.getDateRented().isAfter(inputDateTime) &&
                existRental.getDateDue().isBefore(inputDateTime);
    }


    @Transactional
    public boolean carReturn(String givenLicensePlateNum){
        RentCar rentCar = entityManager.find(RentCar.class, givenLicensePlateNum);
        if(rentCar==null) return false;

        // 아래 작업 이전에 Previous Rental에 데이터 넣는 과정 필요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        rentCar.setCno(null);
        rentCar.setDateRented(null);
        rentCar.setDateDue(null);
        RentCar modified = entityManager.merge(rentCar);

        entityManager.persist(modified);

        return true;
    }

}
