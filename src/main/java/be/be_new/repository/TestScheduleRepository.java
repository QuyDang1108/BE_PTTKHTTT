package be.be_new.repository;

import be.be_new.entity.TestSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestScheduleRepository extends JpaRepository<TestSchedule, Integer> {
}
