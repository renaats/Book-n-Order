package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.Random;

import nl.tudelft.oopp.demo.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    /**
     * GET Endpoint to retrieve a random user.
     *
     * @return randomly selected {@link User}.
     */
    @GetMapping("user")
    @ResponseBody
    public User getRandomUser() {
        User q1 = new User(
                1,
                "Andy",
                9
        );

        User q2 = new User(
                2,
                "Thomas",
                17
        );

        User q3 = new User(
                3,
                "Stefan",
                13
        );

        ArrayList<User> users = new ArrayList<>();
        users.add(q1);
        users.add(q2);
        users.add(q3);

        return users.get(new Random().nextInt(users.size()));
    }
}
