package cnu2023.cnu_database_termproject_2023.previousrental;


import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreviousRentalService {
    private final PreviousRentalRepository previousRentalRepository;

    public PreviousRentalService(PreviousRentalRepository previousRentalRepository) {
        this.previousRentalRepository = previousRentalRepository;
    }

    public PreviousRental save(PreviousRentalDto dto){ // 이전 대여 내역 저장
        PreviousRental previousRental= dto.toEntity();
        return previousRentalRepository.save(previousRental);
    }

    public List<PreviousRental> findAllByCno(String cno){ // 고객의 이전 대여내역 찾기
        return previousRentalRepository.findAll().stream().
                filter(rental->rental.getCustomer().getCno().equals(cno)).
                collect(Collectors.toList());
    }


    public List<ResponseDtoForPreviousRental> convertPreviousRentalToDto(String cno){ // 엔터티를 반환 객체로 변환
        return findAllByCno(cno).stream().
                map(rental->ResponseDtoForPreviousRental.builder()
                        .rentCar(rental.getRentCar())
                        .dateRented(rental.getDateRented())
                        .dateReturned(rental.getDateReturned())
                        .payment(rental.getPayment()).build()).sorted((o1, o2) -> o1.getDateRented().compareTo(o2.getDateRented())).toList();
    }

}
