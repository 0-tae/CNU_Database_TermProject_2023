package cnu2023.cnu_database_termproject_2023.rentcar;

import cnu2023.cnu_database_termproject_2023.customer.Customer;
import cnu2023.cnu_database_termproject_2023.mail.MailService;
import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRental;
import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalDto;
import cnu2023.cnu_database_termproject_2023.previousrental.PreviousRentalService;
import cnu2023.cnu_database_termproject_2023.reserve.Reserve;
import cnu2023.cnu_database_termproject_2023.reserve.ReserveService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class RentCarService {

    private final RentCarRepository rentCarRepository;
    private final ReserveService reserveService;
    private final PreviousRentalService previousRentalService;
    private final EntityManager entityManager;
    private final MailService mailService;

    public RentCarService(RentCarRepository rentCarRepository, ReserveService reserveService, PreviousRentalService previousRentalService, EntityManager entityManager, MailService mailService) {
        this.rentCarRepository = rentCarRepository;
        this.reserveService = reserveService;
        this.previousRentalService = previousRentalService;
        this.entityManager = entityManager;
        this.mailService = mailService;
    }

    @Transactional
    public void switchReservationToRent(LocalDate today){
        reserveService.getReservationList(today).forEach(reserve->{
            RentCar rentCar= rentCarRepository.findById(reserve.getRentCar().getLicensePlateNo()).get();
            Customer customer=reserve.getCustomer();

            customer.setRenting(1);

            rentCar.setCustomer(reserve.getCustomer());
            rentCar.setDateRented(reserve.getStartDate());
            rentCar.setDateDue(reserve.getEndDate());
            entityManager.persist(rentCar);
            entityManager.persist(customer);

            reserveService.cancelReserve(rentCar.getLicensePlateNo(),reserve.getStartDate());
        });


    }

    public List<RentCar> searchFullFilteredRentCars(SearchDto searchDto){
        List<RentCar> availableList=findAvailableRentCars(searchDto);
        List<RentCar> filteredList;

        filteredList=availableList.stream().filter(rentCar->{
            Reserve reserve=reserveService.findReserveByLicence(rentCar.getLicensePlateNo(),rentCar.getDateRented());
            return reserve==null || !(reserveService.isReserveTimeConflict(reserve,searchDto.startDate)
                    || reserveService.isReserveTimeConflict(reserve,searchDto.endDate));
        }).collect(Collectors.toList());

        return filteredList;
    }

    public List<RentCar> findAvailableRentCars(SearchDto searchDto){
        List<RentCar> entierList = findRentCarsAll();
        List<RentCar> filteredList;

        filteredList=entierList.stream().filter(rentCar->
                (searchDto.vehicleType.equals("entire") || rentCar.getCarModel().getVehicleType().equals(searchDto.vehicleType))
                &&!(isRentalTimeConflict(rentCar,searchDto.startDate) || isRentalTimeConflict(rentCar,searchDto.endDate)))
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

    public boolean isRentalTimeConflict(RentCar existRental, LocalDate inputDateTime){
        if(existRental.getDateRented()==null || existRental.getDateDue()==null)
            return false; // 대여중이지 않은 것

        return (existRental.getDateRented().isAfter(inputDateTime) &&
                existRental.getDateDue().isBefore(inputDateTime)) ||
                (existRental.getDateRented().isEqual(inputDateTime)
                || existRental.getDateDue().isEqual(inputDateTime));
    }

    public int paymentCalculation(RentCar rentCar, int totalDay){
        return rentCar.getCarModel().getRentRatePerDay()*(totalDay+1);
    }
    public PreviousRentalDto createPreviousRentalData(RentCar rentCar){
        // LocalDate returnDate1=LocalDate.now();
        LocalDate returnDate2=rentCar.getDateDue();
        int perDay=(int)rentCar.getDateRented().until(returnDate2, ChronoUnit.DAYS);
        int payment=paymentCalculation(rentCar,perDay);

        return PreviousRentalDto.builder().
                rentCar(rentCar).
                dateRented(rentCar.getDateRented()).
                customer(rentCar.getCustomer()).
                dateReturned(returnDate2).
                payment(perDay*payment).build();
    }
    @Transactional
    public boolean carReturn(String givenLicensePlateNum, String cno){
        RentCar rentCar = entityManager.find(RentCar.class, givenLicensePlateNum);

        if(rentCar==null || !rentCar.getCustomer().getCno().equals(cno)) return false;

        PreviousRental previousRental=previousRentalService.save(createPreviousRentalData(rentCar));

        try {
            Thread mailSendingThread = new Thread(() -> mailService.sendMail(previousRental));
            mailSendingThread.start();
        }catch (IllegalThreadStateException e){
            System.out.println("MAIL 전송 실패");
        }
        // 렌트카 정보 초기화 이전에 이전 대여정보를 생성 후 메일통보
        Customer customer = rentCar.getCustomer();
        customer.setRenting(0);
        entityManager.persist(customer);

        rentCar.setCustomer(null);
        rentCar.setDateRented(null);
        rentCar.setDateDue(null);
        RentCar modified = entityManager.merge(rentCar);

        entityManager.persist(modified);
        return true;
    }
}
