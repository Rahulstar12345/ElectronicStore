package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.ApiResponseMessage;
import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // create

    /**
     * @author Rahul_Sonawane
     * @apiNote :Create User
      * @param userDto
     * @return user
     */
    @PostMapping
    public ResponseEntity<UserDto>  createUser(@RequestBody UserDto userDto) {
        logger.info("Before Initializing createUser Method of Service:{}"+userDto);
        UserDto user = userService.createUser(userDto);
        logger.info("After Execution Creating User Method in Database  :{}"+userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


        // update

    /**
     * @author Rahul_Sonawane
     * @apiNote Update User
     * @param userDto
     * @param userId
     * @return userDto1
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable String userId) {
        logger.info("Before Initializing updateUser Method Of Service for Id:{}"+userDto);
        UserDto userDto1 = userService.updateUser(userDto, userId);
        logger.info("After Execution Updating User Method in Database:{}"+userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

        // delete

    /**
     * @author Rahul_Sonawane
     * @apiNote :Delete User
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        logger.info("Before Initializing deleteUser Method Of Service for Id :{}"+userId);
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message(AppConstants.USER_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("After Execution deleting User Method in Database :{}"+userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


        // get all

    /**
     * @author Rahul_Sonawane
     * @apiNote Get All Users
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        logger.info("Initializing getAllUser Method of Service:{}");
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);

    }


        // get single

    /**
     * @author Rahul_Sonawane
     * @apiNote Get Single User By Id
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("Initializing getUserById Method of Service for Id:{}"+userId);
        return  new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }



        // get by email

    /**
     * @author Rahul_Sonawane
     * @apiNote Get User By Email
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Initializing getUserByEmail Method Of Service for Email:{}"+email);
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }


        // search user

    /**
     * @author Rahul_Sonawane
     * @apiNote Search User
     * @param keywords
     * @return
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        logger.info("Initializing searchUser Method Of Service for Keyword:{}"+keywords);
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }


   }
