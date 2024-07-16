package ru.hse.smart_pro.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.smart_pro.data.models.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    int deleteByUsername(String username);
}
