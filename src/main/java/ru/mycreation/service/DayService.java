package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.entities.Days;
import ru.mycreation.repository.DayRepository;

import java.util.List;

@Service
public class DayService {

    private DayRepository dayRepository;

    @Autowired
    public void setDayRepository(DayRepository dayRepository){
        this.dayRepository = dayRepository;
    }

    public List<Days> findAll(){
        return dayRepository.findAll();
    }

    public List<Days> findAllRus() { return  dayRepository.findAllRus(); }

    public List<Days> findAllEn() { return  dayRepository.findAllEn(); }
}
