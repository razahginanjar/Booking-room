package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.service.UserService;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final EmployeeServiceImpl employeeServiceImpl;

    @Transactional(readOnly = true)
    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user is not found")
        );
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(String id) {
        User byId = getById(id);
        String employeeId = byId.getEmployee().getEmployeeId();
        userRepository.delete(byId);
        employeeServiceImpl.removeEmployee(employeeId);
    }

    @Transactional(readOnly = true)
    @Override
    public User getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getPrincipal().toString()).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account do not exist")
        );
    }


}
