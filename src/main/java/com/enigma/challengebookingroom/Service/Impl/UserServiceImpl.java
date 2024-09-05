package com.enigma.challengebookingroom.Service.Impl;

import com.enigma.challengebookingroom.Service.EmployeeService;
import com.enigma.challengebookingroom.Service.UserService;
import com.enigma.challengebookingroom.dto.response.Auth.LoginResponse;
import com.enigma.challengebookingroom.dto.response.Auth.RegisterResponse;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final EmployeeService employeeService;

    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user is not found")
        );
    }

    @Override
    public void remove(String id) {
        User byId = getById(id);
        userRepository.delete(byId);
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user is not found")
//        );
//    }


}
