package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mycreation.entities.WeekTargets;
import ru.mycreation.repository.WeekRepository;

import java.util.List;

@Service
public class WeekService {

    private WeekRepository weekRepository;

    @Autowired
    public void setWeekRepository(WeekRepository weekRepository){
        this.weekRepository = weekRepository;
    }

    public List<WeekTargets> findAll(){
        return weekRepository.findAll();
    }

    public WeekTargets save(WeekTargets week) {
        return weekRepository.save(week);
    }
}
