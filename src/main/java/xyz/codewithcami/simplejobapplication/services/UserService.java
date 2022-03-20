package xyz.codewithcami.simplejobapplication.services;

import xyz.codewithcami.simplejobapplication.models.ApplicationUser;

import java.util.List;

public interface UserService {
    ApplicationUser saveUser(ApplicationUser user);

    ApplicationUser getUser(String email);

    ApplicationUser findUserById(Long id);

    List<ApplicationUser> getAllUsers();

}
