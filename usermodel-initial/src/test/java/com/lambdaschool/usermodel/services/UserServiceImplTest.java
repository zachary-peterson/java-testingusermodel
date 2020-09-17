package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        /*
        List<User> myList = userService.findAll();
                for (User u : myList)
                {
                    System.out.println(u.getUserid() + " " + u.getUsername());
                }
         */
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findUserById()
    {
        assertEquals("barnbarn", userService.findUserById(11).getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void aa_findUserByIdNotFound()
    {
        assertEquals("", userService.findUserById(1000).getUsername());
    }

    @Test
    public void b_findByNameContaining()
    {
        assertEquals(1, userService.findByNameContaining("miss").size());
    }

    @Test
    public void c_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void d_delete()
    {
        userService.delete(14);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void e_findByName()
    {
        assertEquals("puttat", userService.findByName("puttat").getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ee_findByNameNotFound()
    {
        assertEquals("failed", userService.findByName("Testing").getUsername());
    }

    @Test
    public void f_save()
    {
        String testName = "java";

        Role testRole = new Role("test");
        testRole.setRoleid(1);

        User u2 = new User(testName,
            "thispass",
            "cantbesame@why.com");

        u2.getRoles().add(new UserRoles(u2, testRole));

        User test = userService.save(u2);

        assertNotNull(test);
        assertEquals(testName, test.getUsername());
    }

    @Test
    public void g_update()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        String user5Name = "puttat";
        User u4 = new User(user5Name,
            "password",
            "puttat@school.lambda");
        u4.setUserid(13);
        u4.getRoles()
            .add(new UserRoles(u4, r2));
        User addUser = userService.update(u4,13);
        assertNotNull(addUser);
        assertEquals(user5Name,addUser.getUsername());
    }


    @Test
    public void h_deleteAll()
    {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}