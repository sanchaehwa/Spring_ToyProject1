package me.hwayeong.springbootdeveloper.repository;

import me.hwayeong.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //사용자정보를 이메일로 찾음.
    Optional<User> findByEmail(String email);
}
