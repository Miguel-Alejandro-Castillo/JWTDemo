package ar.com.demo.jwt.services;

import ar.com.demo.jwt.dto.PhoneDTO;
import ar.com.demo.jwt.dto.UserDTO;
import ar.com.demo.jwt.model.Phone;
import ar.com.demo.jwt.model.User;
import ar.com.demo.jwt.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    private User user;

    private UserDTO userDTO;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("PEPE");
        user.setEmail("pepe@gmail.com");
        user.setPassword("12345678");
        Phone phone1 = new Phone();
        phone1.setNumber(5374112L);
        phone1.setCityCode(221);
        phone1.setCountryCode("549");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        user.setPhones(phones);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("PEPE");
        userDTO.setEmail("pepe@gmail.com");
        userDTO.setPassword("12345678");

        PhoneDTO phone2 = new PhoneDTO();
        phone2.setNumber(5374112L);
        phone2.setCityCode(221);
        phone2.setCountryCode("549");
        List<PhoneDTO> phones2 = new ArrayList<>();
        phones2.add(phone2);
        userDTO.setPhones(phones2);
    }

    @Test
    void save() {
        lenient().when(this.usersService.save(user)).thenReturn(user);
        assertEquals("PEPE", user.getName());
        assertEquals("pepe@gmail.com", user.getEmail());
        assertEquals("12345678", user.getPassword());
        lenient().when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        assertEquals(1, userRepository.findById(1l).get().getId());
    }

    @Test
    void mapTo() {
        lenient().when(this.usersService.mapTo(user)).thenReturn(this.userDTO);
        assertEquals(user.getEmail(), this.userDTO.getEmail());
        assertEquals(user.getPassword(), this.userDTO.getPassword());
        assertEquals(user.getName(), this.userDTO.getName());
        assertEquals(user.getPhones().get(0).getNumber(), userDTO.getPhones().get(0).getNumber());
        assertEquals(user.getPhones().get(0).getCityCode(), userDTO.getPhones().get(0).getCityCode());
        assertEquals(user.getPhones().get(0).getCountryCode(), userDTO.getPhones().get(0).getCountryCode());
    }

    @Test
    void testMapTo() {
        lenient().when(this.usersService.mapTo(userDTO)).thenReturn(user);
        assertEquals(user.getEmail(), this.userDTO.getEmail());
        assertEquals(user.getPassword(), this.userDTO.getPassword());
        assertEquals(user.getName(), this.userDTO.getName());
        assertEquals(user.getPhones().get(0).getNumber(), userDTO.getPhones().get(0).getNumber());
        assertEquals(user.getPhones().get(0).getCityCode(), userDTO.getPhones().get(0).getCityCode());
        assertEquals(user.getPhones().get(0).getCountryCode(), userDTO.getPhones().get(0).getCountryCode());
    }

    @Test
    void findByEmailAndPassword() {
        lenient().when(this.usersService.findByEmailAndPassword("pepe@gmail.com", "12345678")).thenReturn(this.user);
        assertEquals("pepe@gmail.com", this.user.getEmail());
        assertEquals("12345678", this.user.getPassword());
    }

    @Test
    void findByEmail() {
        lenient().when(this.usersService.findByEmail("pepe@gmail.com")).thenReturn(this.user);
        assertEquals("pepe@gmail.com", this.user.getEmail());
    }
}