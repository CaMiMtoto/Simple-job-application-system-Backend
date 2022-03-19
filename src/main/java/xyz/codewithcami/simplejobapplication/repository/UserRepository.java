package xyz.codewithcami.simplejobapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.codewithcami.simplejobapplication.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
