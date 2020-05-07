package ru.mycreation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mycreation.entities.WeekTargets;

@Repository
public interface WeekRepository extends JpaRepository<WeekTargets, Long> {
}
