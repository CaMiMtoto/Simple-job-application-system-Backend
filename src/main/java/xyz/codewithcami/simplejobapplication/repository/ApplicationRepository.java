package xyz.codewithcami.simplejobapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.codewithcami.simplejobapplication.models.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
