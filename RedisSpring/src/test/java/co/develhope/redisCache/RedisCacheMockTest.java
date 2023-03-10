package co.develhope.redisCache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import co.develhope.redisCache.entities.jpa.UserJPA;
import co.develhope.redisCache.entities.redis.UserRedis;
import co.develhope.redisCache.repositories.jpa.UserRepositoryJPA;
import co.develhope.redisCache.repositories.redis.UserRepositoryRedis;
import co.develhope.redisCache.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisCacheMock.class)
public class RedisCacheMockTest {

    @Autowired
    private UserRepositoryRedis userRepositoryRedis;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    @Test
    public void shouldWriteOnRedisCache(){
        UserRedis userRedis = new UserRedis();
        userRedis.setDomicileCity("Milano");
        userRedis.setId(1L);
        userRedis.setEmail("email@email.com");
        userRedis.setFirstName("MuName");
        UserRedis userSavedInRedisCache = userRepositoryRedis.save(userRedis);
        Assertions.assertNotNull(userSavedInRedisCache);
    }

    @Test
    public void shouldCreateUser(){
        UserJPA newUser = new UserJPA();
        newUser.setFirstName("Gigi");
        newUser.setDomicileCity("Reggio Emilia");
        newUser.setEmail("gigi@ciao");

        UserJPA savedUser = userRepositoryJPA.save(newUser);

        assertNotNull(savedUser.getId());
        assertEquals(newUser.getFirstName(),savedUser.getFirstName());
        assertEquals(newUser.getDomicileCity(),savedUser.getDomicileCity());
        assertEquals(newUser.getEmail(),savedUser.getEmail());

    }

    @Test
    public void shouldGetUserFromCache(){
        UserJPA newUser = new UserJPA();
        UserJPA foundUser = userRepositoryJPA.getById(newUser.getId());

        assertNotNull(foundUser);
        assertEquals(newUser.getFirstName(), foundUser.getFirstName());
        assertEquals(newUser.getDomicileCity(), foundUser.getDomicileCity());
        assertEquals(newUser.getEmail(), foundUser.getEmail());
    }

    @Test
    public void shouldDeleteUser(){
        //userService.delete();
    }

    @Test
    public void shouldUpdateUser(){
        //userService.update();
    }
}
