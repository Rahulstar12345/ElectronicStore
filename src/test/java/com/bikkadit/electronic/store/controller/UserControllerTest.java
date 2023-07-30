package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.entity.User;
import com.bikkadit.electronic.store.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    public void init(){
         user = User.builder()
                .name("Aaryan")
                .email("aryan@gmail.com")
                .password("aryan645")
                .gender("Male")
                .about("This is testing for create method")
                .imageName("aryan.png")
                .build();

    }

    @Test
    public void createUserTest() throws Exception {

        // users +POST+ user data as json
        // data as json+status created

        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        // actual request for url

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    public void updateUserTest() throws Exception {
        //  /user/{userId} + PUT request + json

        String userId="123";

        UserDto dto = this.mapper.map(user, UserDto.class);

        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }


    private String convertObjectToJsonString(Object user) {
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }

}
