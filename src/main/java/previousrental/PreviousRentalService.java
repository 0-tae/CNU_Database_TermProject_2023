package previousrental;


import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import rentcar.RentCar;

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
        return previousRental;
    }

    public List<PreviousRental> findAllByCno(String cno){
        return previousRentalRepository.findAll().stream().
                filter(rental->rental.getCustomer().getCno().equals(cno)).
                collect(Collectors.toList());
    }


    public List<ResponseDtoForPreviousRental> convertPreviousRentalToDto(String cno){
        List<PreviousRental> rentalList=findAllByCno(cno);
        List<ResponseDtoForPreviousRental> response=
                rentalList.stream().
                        map(rental->ResponseDtoForPreviousRental.builder()
                                .licensePlateNum(rental.getLicensePlateNo())
                                .modelName(entityManager.find(RentCar.class,rental.getLicensePlateNo()).getCarModel().getModelName())
                                .dateRented(rental.getDateRented())
                                .dateReturned(rental.getDateReturned())
                                .payment(rental.getPayment()).build()).toList();

        return response;
    }

}
