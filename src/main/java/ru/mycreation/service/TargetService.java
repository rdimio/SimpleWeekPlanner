package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.entities.User;
import ru.mycreation.repository.TargetRepository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class TargetService {

    private TargetRepository targetRepository;

    @Autowired
    public void setTargetRepository(TargetRepository targetRepository){
        this.targetRepository = targetRepository;
    }

    @Transactional
    public DayTargets save(DayTargets target){
        return targetRepository.save(target);
    }

    public Set<String> findDistinctTitle(User user) { return targetRepository.findDistinctTitle(user);
    }

    @Transactional
    public void delete(Long id) {
        targetRepository.deleteById(id);
    }

    public DayTargets findById(Long id) {
        return targetRepository.findById(id).get();
    }

    public List<String> selectSumTimeByUserIdGroupByPriority(User user) {
        List<String> priorities = new ArrayList<>();
        for(String p : targetRepository.selectSumTimersByUserIdGroupByPriority(user)){
            String s = p.replace(',','-');
            priorities.add(s);
        }
        return priorities;
    }

    public List<String> selectSumTimeByUserIdGroupByCreation(User user) {
        List<String> creations = new ArrayList<>();;
        for(String c : targetRepository.selectSumTimersByUserIdGroupByCreation(user)){
            String s = c.replace(',','-');
            creations.add(s);
        }
        return creations;
    }

}
