package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.repository.TargetRepository;

import java.util.List;
import java.util.Set;

@Service
public class TargetService {

    private TargetRepository targetRepository;

    @Autowired
    public void setTargetRepository(TargetRepository targetRepository){
        this.targetRepository = targetRepository;
    }

    public List<DayTargets> findAll(){
        return targetRepository.findAll();
    }

    public DayTargets save(DayTargets target){
        return targetRepository.save(target);
    }

    public Set<String> findDistinctTitle() { return targetRepository.findDistinctTitle();
    }
}
