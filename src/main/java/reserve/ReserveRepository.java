package reserve;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rentcar.RentCar;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve,String> {

}
