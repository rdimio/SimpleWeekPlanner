package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.repository.TargetRepository;

import java.util.Set;

@Service
public class TargetService {

    private TargetRepository targetRepository;

    @Autowired
    public void setTargetRepository(TargetRepository targetRepository){
        this.targetRepository = targetRepository;
    }

    public DayTargets save(DayTargets target){
        return targetRepository.save(target);
    }

    public Set<String> findDistinctTitle() { return targetRepository.findDistinctTitle();
    }
    @Transactional
    public void delete(Long id) {
        targetRepository.deleteById(id);
    }

    public DayTargets findById(Long id) {
        return targetRepository.findById(id).get();
    }

}
