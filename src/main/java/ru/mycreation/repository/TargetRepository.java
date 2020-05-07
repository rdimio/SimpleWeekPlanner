package ru.mycreation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mycreation.entities.DayTargets;

@Repository
public interface TargetRepository extends JpaRepository<DayTargets, Long> {
}
