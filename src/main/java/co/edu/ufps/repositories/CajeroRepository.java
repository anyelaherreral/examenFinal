package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.entities.Cajero;

@Repository
public interface CajeroRepository extends JpaRepository<Cajero,Integer>{

}
