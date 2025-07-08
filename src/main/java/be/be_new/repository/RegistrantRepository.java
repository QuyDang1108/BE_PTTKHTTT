package be.be_new.repository;

import be.be_new.entity.Registrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrantRepository extends JpaRepository<Registrant, Integer> {
}
