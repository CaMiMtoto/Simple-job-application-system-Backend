package xyz.codewithcami.simplejobapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.codewithcami.simplejobapplication.models.ApplicationUser;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByEmail(String email);
}
