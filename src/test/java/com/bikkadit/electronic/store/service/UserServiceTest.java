package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.entity.User;
import com.bikkadit.electronic.store.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    public void init(){
         user = User.builder()
                .name("Rahul")
                .email("rahul@gmail.com")
                .password("rahul23")
                .gender("Male")
                .about("This is testing for create method")
                .imageName("rahu.png")
                .build();
    }

    // create testing for user
    @Test
    public void createUserTest(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
      //  System.out.println(user1.getName());
     //   Assertions.assertNotNull(user1);
        Assertions.assertEquals("Rahul",user1.getName());
    }

    // update testing for user
    @Test
    public void updateUserTest(){
        String userId="";
        UserDto userDto = UserDto.builder()
                .name("Rahul Pradip Sonawane")
                .gender("Male")
                .about("This is updated user about details")
                .imageName("xyz.png")
                .build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, userId);
     //   UserDto updateUser = mapper.map(user, UserDto.class);
        System.out.println(updateUser.getName());
        System.out.println(updateUser.getGender());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(),updateUser.getName(),"Name is not valid !!");
    }

    // delete user test
    @Test
    public void deleteUserTest(){

        String userId="useridabd";
        Mockito.when(userRepository.findById("useridabd")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);

    }

    // get all user test
    @Test
    public void getAllUserTest(){

      User  user1 = User.builder()
                .name("Nital")
                .email("nital@gmail.com")
                .password("nit1232")
                .gender("Female")
                .about("This is testing for create method")
                .imageName("nit.png")
                .build();

       User user2 = User.builder()
                .name("Aarya")
                .email("aarya@gmail.com")
                .password("arya54")
                .gender("Male")
                .about("This is testing for create method")
                .imageName("arya.png")
                .build();

        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page=new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
     //   Sort sort=Sort.by("name").ascending();
   //     Pageable pageable = PageRequest.of(1, 2, sort);
        PageableResponse<UserDto> allUser = userService.getAllUser(1,2,"name","asc");
        System.out.println(allUser.getTotalElements());
        Assertions.assertEquals(3,allUser.getContent().size());

    }

    // get all users Test

    @Test
    public void getUserByIdTest(){

        String userId="userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // actual call of service method
        UserDto userDto = userService.getUserById(userId);
        System.out.println(userDto.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"Name is Not Match !!");
    }

    // get user by email id test
    @Test
    public void getUserByEmailTest(){

        String emailId="rahul@gmail.com";
        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByEmail(emailId);
        System.out.println(userDto.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"Email is not matched !!");


    }

    // search user
    @Test
    public void searchUserTest(){

        User  user1 = User.builder()
                .name("Nital Sonawane")
                .email("nital@gmail.com")
                .password("nit1232")
                .gender("Female")
                .about("This is testing for create method")
                .imageName("nit.png")
                .build();

        User user2 = User.builder()
                .name("Aarya Sonawane")
                .email("aarya@gmail.com")
                .password("arya54")
                .gender("Male")
                .about("This is testing for create method")
                .imageName("arya.png")
                .build();

        User user3 = User.builder()
                .name("Aaryan")
                .email("aryan@gmail.com")
                .password("aryan645")
                .gender("Male")
                .about("This is testing for create method")
                .imageName("aryan.png")
                .build();

        String keywords="Sonawane";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user,user1,user2,user3));

        List<UserDto> userDtos = userService.searchUser(keywords);

        Assertions.assertEquals(4,userDtos.size(),"Size Not Match !!");

    }

//    public void findUserByEmailOptionalTest(){
//        String email="rahul@gmail.com";
//        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        Optional<User> userByEmailOptional=userService.findUserByEmailOptional(email);
//        Assertions.assertTrue(userByEmailOptional.isPresent());
//
//        User user1 = userByEmailOptional.get();
//        Assertions.assertEquals(user.getEmail(),user1.getEmail(),"email not matched !!");
//    }

}
