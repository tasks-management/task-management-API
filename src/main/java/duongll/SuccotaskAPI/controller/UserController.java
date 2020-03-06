package duongll.SuccotaskAPI.controller;

import duongll.SuccotaskAPI.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @RequestMapping(value = "/api/v1/user/test", method = RequestMethod.GET)
    @ResponseBody
    private ResponseEntity<Object> test() {
        User user = new User();
        user.setId(new Long(1));
        user.setName("Duong");
        user.setAge(50);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
