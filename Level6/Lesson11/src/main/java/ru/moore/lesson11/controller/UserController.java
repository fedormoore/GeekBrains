package ru.moore.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.moore.lesson11.model.User;
import ru.moore.lesson11.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/score/inc")
    public String addScoreOnOne(Principal p) {
        User user = userService.findUserByName(p.getName()).orElseThrow(() -> new UsernameNotFoundException("No such user in DB" + p.getName()));
        user.setScore(user.getScore() + 1);
        userService.saveUser(user);
        return "Score update for user - " + user.getLogin();
    }

    @GetMapping("/score/dec")
    public String reduceScoreOnOne(Principal p) {
        User user = userService.findUserByName(p.getName()).orElseThrow(() -> new UsernameNotFoundException("No such user in DB" + p.getName()));
        user.setScore(user.getScore() - 1);
        userService.saveUser(user);
        return "Score update for user - " + user.getLogin();
    }

    @GetMapping("/score/get/current")
    public String getCurrent(Principal p) {
        User user = userService.findUserByName(p.getName()).orElseThrow(() -> new UsernameNotFoundException("No such user in DB" + p.getName()));
        return "For current user - " + user.getLogin() + " score is - " + user.getScore();
    }

    @GetMapping("/score/get/{id}")
    public String getScoreByUserId(@PathVariable Long id, Principal p) {
        User user = userService.findUserById(id);
        return "Yours score is - " + user.getScore();
    }

}
