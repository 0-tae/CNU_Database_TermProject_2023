package previousrental;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreviousRentalRepository extends JpaRepository<PreviousRental,String> {
}
