package previousrental;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreviousRentalService {

    private final PreviousRentalRepository previousRentalRepository;

    public PreviousRentalService(PreviousRentalRepository previousRentalRepository) {
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
}
