package cnu2023.cnu_database_termproject_2023.previousrental;


import cnu2023.cnu_database_termproject_2023.rentcar.RentCar;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreviousRentalService {

    private final EntityManager entityManager;

    private final PreviousRentalRepository previousRentalRepository;

    public PreviousRentalService(EntityManager entityManager, PreviousRentalRepository previousRentalRepository) {
        this.entityManager = entityManager;
        this.previousRentalRepository = previousRentalRepository;
    }

    public PreviousRental save(PreviousRentalDto dto){
        PreviousRental previousRental= dto.toEntity();
        return previousRentalRepository.save(previousRental);
    }

    public List<PreviousRental> findAllByCno(String cno){
        return previousRentalRepository.findAll().stream().
                filter(rental->rental.getCustomer().getCno().equals(cno)).
                collect(Collectors.toList());
    }


    public List<ResponseDtoForPreviousRental> convertPreviousRentalToDto(String cno){
        return findAllByCno(cno).stream().
                map(rental->ResponseDtoForPreviousRental.builder()
                        .licensePlateNum(rental.getLicensePlateNo())
                        .modelName(entityManager.find(RentCar.class,rental.getLicensePlateNo()).getCarModel().getModelName())
                        .dateRented(rental.getDateRented())
                        .dateReturned(rental.getDateReturned())
                        .payment(rental.getPayment()).build()).sorted((o1, o2) -> o1.getDateRented().compareTo(o2.getDateRented())).toList();
    }

}
