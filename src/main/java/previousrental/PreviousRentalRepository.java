package previousrental;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PreviousRentalRepository extends JpaRepository<PreviousRental,PreviousRentalPK> {

}
