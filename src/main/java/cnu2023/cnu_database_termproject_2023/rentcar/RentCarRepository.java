package cnu2023.cnu_database_termproject_2023.rentcar;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentCarRepository extends JpaRepository<RentCar,String> {

}
