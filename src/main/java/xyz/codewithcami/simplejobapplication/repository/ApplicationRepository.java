package xyz.codewithcami.simplejobapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.codewithcami.simplejobapplication.models.Application;
import xyz.codewithcami.simplejobapplication.models.User;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
