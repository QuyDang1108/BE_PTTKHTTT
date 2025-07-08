package be.be_new.repository;

import be.be_new.entity.ExaminationPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationPermitRepository extends JpaRepository<ExaminationPermit, Integer> {
}
