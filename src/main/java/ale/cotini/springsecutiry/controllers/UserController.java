package ale.cotini.springsecutiry.controllers;

import ale.cotini.springsecutiry.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService usersService) {
        this.userService = usersService;
    }
}
