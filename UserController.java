package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.ApiResponseMessage;
import com.bikkadit.electronic.store.dto.ImageResponse;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.service.FileService;
import com.bikkadit.electronic.store.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    // create

    /**
     * @author Rahul_Sonawane
     * @apiNote :Create User
      * @param userDto
     * @return user
     */
    @PostMapping
    public ResponseEntity<UserDto>  createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Before Initializing createUser Method of Controller:{}"+userDto);
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
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable String userId) {
        logger.info("Before Initializing updateUser Method Of Controller for Id:{}"+userDto);
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
        logger.info("Before Initializing deleteUser Method Of Controller for Id :{}"+userId);
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
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir) {
        logger.info("Initializing getAllUser Method of Controller:{}");
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);

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
        logger.info("Initializing getUserById Method of Controller for Id:{}"+userId);
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
        logger.info("Initializing getUserByEmail Method Of Controller for Email:{}"+email);
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
        logger.info("Initializing searchUser Method Of Controller for Keyword:{}"+keywords);
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }

    // upload user image

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);

        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message(AppConstants.IMAGE_UPLOADED).success(true).status(HttpStatus.CREATED).build();
        return  new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    //serve user image

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getUserById(userId);
        logger.info("User image name : {} ",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

   }