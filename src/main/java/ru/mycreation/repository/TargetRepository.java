package ru.mycreation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.entities.User;

import java.util.List;
import java.util.Set;

@Repository
public interface TargetRepository extends JpaRepository<DayTargets, Long> {

    @Query("SELECT DISTINCT d.title FROM DayTargets d WHERE d.user = :user")
    Set<String> findDistinctTitle(User user);

    @Query("SELECT d.priority, SUM(d.time) FROM DayTargets d WHERE d.user = :user GROUP BY d.priority")
    List<String> selectSumTimersByUserIdGroupByPriority(User user);

    @Query("SELECT d.creation, SUM(d.time) FROM DayTargets d WHERE d.user = :user GROUP BY d.creation")
    List<String> selectSumTimersByUserIdGroupByCreation(User user);
}
