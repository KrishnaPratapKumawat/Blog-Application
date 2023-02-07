package com.codewithkpk.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithkpk.blog.entity.User;
import com.codewithkpk.blog.payloads.UserDataTransferOption;
import com.codewithkpk.blog.repo.UserRepository;
import com.codewithkpk.blog.services.UserServices;
import com.codewithkpk.blog.exception.ResourceNotFoundException;


@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDataTransferOption addUserData(UserDataTransferOption udto) {
        User user = this.dtoToUser(udto);
        User savedUser = this.userRepository.save(user);
        return this.userToudto(savedUser);
    }


       @Override
    public UserDataTransferOption updateUserData(UserDataTransferOption udto, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        user.setUserName(udto.getUserName());
        user.setEmail(udto.getEmail());
        user.setPassword(udto.getPassword());
        user.setAbout(udto.getAbout());
        final User updateUsr = this.userRepository.save(user);
        return this.userToudto(updateUsr);

    }

    @Override
    public UserDataTransferOption getUserData(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        return this.userToudto(user);
    }

    @Override
    public List<UserDataTransferOption> getAllUserData() {
        List<User> users = this.userRepository.findAll();
        List<UserDataTransferOption> userDtos = users.stream().map(user -> this.userToudto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUserData(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        this.userRepository.delete(user);

    }

    public UserDataTransferOption userToudto(User user) {
        UserDataTransferOption userDto = this.modelMapper.map(user,UserDataTransferOption.class);
        return userDto;

    }

    public User dtoToUser(UserDataTransferOption udto) {
        User user = this.modelMapper.map(udto, User.class);
        return user;

    }

}
