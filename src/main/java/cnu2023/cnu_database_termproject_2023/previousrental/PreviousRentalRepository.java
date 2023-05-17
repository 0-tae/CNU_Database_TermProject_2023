package cnu2023.cnu_database_termproject_2023.previousrental;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreviousRentalRepository extends JpaRepository<PreviousRental,PreviousRentalPK> {

}
