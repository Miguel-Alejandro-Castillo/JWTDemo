package ar.com.demo.jwt.repositories;

import ar.com.demo.jwt.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
