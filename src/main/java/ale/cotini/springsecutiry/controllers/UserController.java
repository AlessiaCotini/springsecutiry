package ale.cotini.springsecutiry.controllers;

import ale.cotini.springsecutiry.entities.User;
import ale.cotini.springsecutiry.records.UserDTO;
import ale.cotini.springsecutiry.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UserController {
    private final UserService userService;

    public UserController(UserService usersService) {
        this.userService = usersService;
    }

    @PostMapping
    //@PreAuthorize ("hasAnyAuthority('ADMIN')")
    public User createUtente (@RequestBody UserDTO utente){
      return  this.userService.save(utente);
    }

    @GetMapping
    public List<User> getAll(){
        return this.userService.findAll();
    }

    @GetMapping("/me")
    public User getMyProfile (@AuthenticationPrincipal User myProfile){
        return myProfile;
    }
}
