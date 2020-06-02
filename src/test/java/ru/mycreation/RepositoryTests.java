package ru.mycreation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.entities.Days;
import ru.mycreation.entities.Role;
import ru.mycreation.entities.User;
import ru.mycreation.repository.DayRepository;
import ru.mycreation.repository.RoleRepository;
import ru.mycreation.repository.TargetRepository;
import ru.mycreation.repository.UserRepository;

import java.sql.Time;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void dayRepositoryTest() {
        List<Days> rusList = dayRepository.findAllRus();
        List<Days> enList = dayRepository.findAllEn();
        String[] rus = {"Понедельник","Вторник", "Среда","Четверг","Пятница","Суббота","Воскресенье"};
        String[] en = {"Monday","Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
        Iterator<Days> iterRus = rusList.iterator();
        Iterator<Days> iterEn = enList.iterator();
        int count = 0;
        while(iterRus.hasNext()){
            Days day = iterRus.next();
            assertEquals(rus[count], day.getTitle());
            count++;
        }
        count = 0;
        while(iterEn.hasNext()){
            Days day = iterEn.next();
            assertEquals(en[count], day.getTitle());
            count++;
        }
    }

    @Test
    public void TargetRepositoryTest(){
        Role role = new Role("USER");
        entityManager.persistAndFlush(role);
        User user = new User(null, "12345", "ivan", "ivan@mail.ru", roleRepository.findAll());
        entityManager.persistAndFlush(user);
        DayTargets target1 = new DayTargets(null, "медитация", 4,
                "д", new Time(00,15,00), user);
        entityManager.persistAndFlush(target1);
        DayTargets target2 = new DayTargets(null, "в магазин за продуктами",
                2, "н", new Time(00,45,00), user);
        entityManager.persistAndFlush(target2);
        Set<String> targets = targetRepository.findDistinctTitle(user);
        assertEquals(targets.toArray(), new String[]{"медитация", "в магазин за продуктами"});
    }

}
