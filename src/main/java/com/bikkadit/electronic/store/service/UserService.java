package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.entity.User;

import java.util.List;

public interface UserService {

    // create
     UserDto createUser(UserDto userDto);

    // update
     UserDto updateUser(UserDto userDto,String userId);

    // delete
     void deleteUser(String userId);

    // get all users
     List<UserDto> getAllUser();

    // get single user by id
     UserDto getUserById(String userId);

    // get single user by email
     UserDto getUserByEmail(String email);

    // search user
     List<UserDto> searchUser(String keyword);

    // other user specific feature
}
