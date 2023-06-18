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
    public void switchReservationToRent(LocalDate today) {
        reserveService.getReservationList(today).forEach(reserve -> {
            RentCar rentCar = rentCarRepository.findById(reserve.getRentCar().getLicensePlateNo()).get();
            rentCar.setCustomer(reserve.getCustomer());
            rentCar.setDateRented(reserve.getStartDate());
            rentCar.setDateDue(reserve.getEndDate());
            entityManager.persist(rentCar);
            reserveService.cancelReserve(rentCar.getLicensePlateNo(), reserve.getStartDate());
            // 렌트 시, 정보 저장 후 예약 내역에서 삭제
        });
    }

    public List<RentCar> searchFullFilteredRentCars(SearchDto searchDto) {
        List<RentCar> availableList = findAvailableRentCars(searchDto);
        List<RentCar> filteredList;

        filteredList = availableList.stream().filter(rentCar -> {
            Reserve reserve = reserveService.findReserveByLicence(rentCar.getLicensePlateNo(), rentCar.getDateRented());
            return reserve == null || !(reserveService.isReserveTimeConflict(reserve, searchDto.startDate)
                    || reserveService.isReserveTimeConflict(reserve, searchDto.endDate));
        }).collect(Collectors.toList()); // 예약 가능한 렌터카 목록을 출력

        return filteredList;
    }

    public List<RentCar> findAvailableRentCars(SearchDto searchDto) {
        List<RentCar> entierList = findRentCarsAll();
        List<RentCar> filteredList;

        filteredList = entierList.stream().filter(rentCar ->
                        (searchDto.vehicleType.equals("entire") ||
                                rentCar.getCarModel().getVehicleType().equals(searchDto.vehicleType))
                                && !(isRentalTimeConflict(rentCar, searchDto.startDate) ||
                                isRentalTimeConflict(rentCar, searchDto.endDate)))
                .collect(Collectors.toList());
        // 체크 박스에 해당하는 렌터카를 출력 후 대여 중이지 않은 것을 반환

        return filteredList;
    }

    public List<RentCar> findRentCarsAll() { // 모든 렌트카 내역 출력
        return rentCarRepository.findAll();
    }

    public List<RentCar> findRentCarsAllByCno(String cno) { // 고객이 대여 중인 렌트카 찾기
        return rentCarRepository.findAll().stream().filter(car -> car.getCustomer() != null).
                filter(car -> car.getCustomer().getCno().equals(cno)).toList();
    }

    public boolean isRentalTimeConflict(RentCar existRental, LocalDate inputDateTime) {
        if (existRental.getDateRented() == null || existRental.getDateDue() == null)
            return false; // 대여 중이지 않은 것, 시간 충돌 없음

        return (existRental.getDateRented().isAfter(inputDateTime) &&
                existRental.getDateDue().isBefore(inputDateTime)) ||
                (existRental.getDateRented().isEqual(inputDateTime)
                        || existRental.getDateDue().isEqual(inputDateTime)); // 대여 중 이지만, 시간 충돌 여부 판단.
    }

    public int paymentCalculation(RentCar rentCar, int totalDay) {
        return rentCar.getCarModel().getRentRatePerDay() * (totalDay + 1);
    }

    public PreviousRentalDto createPreviousRentalData(RentCar rentCar) { // 이전 대여내역 만들기
        // LocalDate returnDate1=LocalDate.now();
        LocalDate returnDate2 = rentCar.getDateDue();
        int perDay = (int) rentCar.getDateRented().until(returnDate2, ChronoUnit.DAYS); // 빌린 일 수 확인
        int payment = paymentCalculation(rentCar, perDay); // 날짜 기반으로 결제 금액 계산

        return PreviousRentalDto.builder().
                rentCar(rentCar).
                dateRented(rentCar.getDateRented()).
                customer(rentCar.getCustomer()).
                dateReturned(returnDate2).
                payment(perDay * payment).build();
    }

    @Transactional
    public boolean carReturn(String givenLicensePlateNum, String cno) { // 자동차 반납
        RentCar rentCar = entityManager.find(RentCar.class, givenLicensePlateNum);

        if (rentCar == null || !rentCar.getCustomer().getCno().equals(cno)) return false;
        // 고객명 불일치 예외처리

        PreviousRental previousRental = previousRentalService.save(createPreviousRentalData(rentCar));
        // 이전 대여 정보를 저장

        try {
            Thread mailSendingThread = new Thread(() -> mailService.sendMail(previousRental));
            mailSendingThread.start();
        } catch (IllegalThreadStateException e) {
            System.out.println("MAIL 전송 실패");
        }
        // 렌트카 정보 초기화 이전에 이전 대여정보를 생성 후 메일통보

        rentCar.setCustomer(null);
        rentCar.setDateRented(null);
        rentCar.setDateDue(null);

        RentCar modified = entityManager.merge(rentCar);
        entityManager.persist(modified);

        return true;
    }
}
