package ru.mycreation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mycreation.entities.Days;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Days, Long> {
    @Query("SELECT d FROM Days d WHERE d.id > 7")
    List<Days> findAllEn();

    @Query("SELECT d FROM Days d WHERE d.id <= 7")
    List<Days> findAllRus();
}
