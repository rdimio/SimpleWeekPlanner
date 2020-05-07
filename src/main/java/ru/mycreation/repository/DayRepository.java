package ru.mycreation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mycreation.entities.Days;

@Repository
public interface DayRepository extends JpaRepository<Days, Long> {
}
