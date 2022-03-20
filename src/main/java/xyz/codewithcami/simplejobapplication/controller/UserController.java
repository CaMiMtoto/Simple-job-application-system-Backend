package xyz.codewithcami.simplejobapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.codewithcami.simplejobapplication.models.ApplicationUser;
import xyz.codewithcami.simplejobapplication.services.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ApplicationUser>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/")
    public ResponseEntity<ApplicationUser> createUser(@RequestBody ApplicationUser user) throws URISyntaxException {
        ApplicationUser savedUser = userService.saveUser(user);
        return ResponseEntity.created(
                new URI("/api/users/" + savedUser.getId())
        ).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUser> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

}
