package cnu2023.cnu_database_termproject_2023.reserve;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve,ReservePK> {

}
