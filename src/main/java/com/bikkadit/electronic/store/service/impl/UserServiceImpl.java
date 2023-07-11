package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.entity.User;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Sending request to repository method for create user :{}");

        // generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);


        // dto ->entity
        User user = mapper.map(userDto,User.class);
        User saveUser = userRepository.save(user);

        // entity ->dto
        UserDto newDto = mapper.map(user,UserDto.class);

        logger.info("User Created In Database :{}");
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Sending request to repository method for update user :{}");

        logger.info("");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        user.setName(userDto.getName());
        // email update
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        // save data
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = mapper.map(user,UserDto.class);
        logger.info("User Updated  In Database :{}");

        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Sending request to repository method for delete user :{}");

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        // delete user
        userRepository.delete(user);
        logger.info("User Deleted  In Database :{}");


    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Sending request to repository method for get all user :{}");

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());


        // pageNumber default starts from 0
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        logger.info("Get All User From the Database :{}");

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Sending request to repository method for getallUserById :{}");

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        logger.info("Get Id From the Database :{}");

        return mapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Sending request to repository method for getallUserByEmail :{}");

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        logger.info("Get Id From the Database Using Email :{}");

        return mapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Sending request to repository method for searchUser :{}");

        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> mapper.map(user,UserDto.class)).collect(Collectors.toList());
        logger.info("User Search From  the Database Using Keyword :{}");

        return dtoList;
    }

    // dto to entity
       private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();

        return mapper.map(userDto,User.class);
    }

    // entity to dto
    private UserDto entityToDto(User saveUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(saveUser.getUserId())
//                .name(saveUser.getName())
//                .email(saveUser.getEmail())
//                .password(saveUser.getPassword())
//                .about(saveUser.getAbout())
//                .gender(saveUser.getGender())
//                .imageName(saveUser.getImageName())
//                .build();
        return mapper.map(saveUser,UserDto.class);
    }
}

