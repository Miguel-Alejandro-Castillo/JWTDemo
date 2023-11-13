package ar.com.demo.jwt.services;

import ar.com.demo.jwt.dto.UserDTO;
import ar.com.demo.jwt.model.User;
import ar.com.demo.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Service
public class UsersService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        user.setCreated(LocalDateTime.now());
        user.setIsActive(true);
        return this.userRepository.save(user);
    }

    public User mapTo(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }

    public UserDTO mapTo(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    public User findByEmailAndPassword(String email, String password) {
        User user = this.userRepository.findByEmailAndPassword(email, password).orElse(null);
        return user;
    }

    public User findByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElse(null);
        return user;
    }
}
