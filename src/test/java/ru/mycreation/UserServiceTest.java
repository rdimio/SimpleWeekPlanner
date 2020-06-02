package ru.mycreation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mycreation.entities.User;
import ru.mycreation.repository.UserRepository;
import ru.mycreation.service.UserService;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUserTest() {
        User userFromDB = new User();
        userFromDB.setLogin("John");
        userFromDB.setEmail("John@mail.ru");
        Mockito.doReturn(userFromDB)
                .when(userRepository)
                .findOneByLogin("John");
        User userJohn = userService.findByLogin("John");
        assertNotNull(userJohn);
        Mockito.verify(userRepository, Mockito.times(1)).findOneByLogin(ArgumentMatchers.eq("John"));
//        Mockito.verify(userRepository, Mockito.times(1)).findOneByLogin(ArgumentMatchers.any(String.class));
    }
}
