package com.todolist.service;

import com.todolist.unit.User;
import com.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUser(long id, User user) {
        User userFromDB = userRepository.findById(id).get();
        userFromDB.setFirstname(user.getFirstname());
        userFromDB.setLastname(user.getLastname());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setBirthDate(user.getBirthDate());
        userFromDB.setPassword(user.getPassword());
        userRepository.save(userFromDB);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
