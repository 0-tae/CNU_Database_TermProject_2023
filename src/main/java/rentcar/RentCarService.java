package rentcar;

import org.springframework.stereotype.Service;
import reserve.Reserve;
import reserve.ReserveService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentCarService {

    private final RentCarRepository rentCarRepository;
    private final ReserveService reserveService;

    public RentCarService(RentCarRepository rentCarRepository, ReserveService reserveService) {
        this.rentCarRepository = rentCarRepository;
        this.reserveService = reserveService;
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

    private boolean isRentalTimeConflict(RentCar existRental, LocalDateTime inputDateTime){
        if(existRental.getDateRented()==null || existRental.getDateDue()==null)
            return false; // 대여중이지 않은 것

        return existRental.getDateRented().isAfter(inputDateTime) &&
                existRental.getDateDue().isBefore(inputDateTime);
    }

}
