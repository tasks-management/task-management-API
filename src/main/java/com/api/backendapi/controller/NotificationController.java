package com.api.backendapi.controller;

import com.api.backendapi.dtos.NotificationBackgroundDto;
import com.api.backendapi.entity.User;
import com.api.backendapi.service.iservice.IUserService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class NotificationController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/api/v1/notification/background", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> testMessageNotification(@RequestParam("firebaseToken") String firebaseToken,
                                                          @RequestBody NotificationBackgroundDto dto){
        Message message = Message.builder()
                .setNotification(new Notification(dto.getTitle(), dto.getContent()))
                .setToken(firebaseToken)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // Response is a message ID string.
    }

    @RequestMapping(value = "/api/v1/user/{id}/notification/refresh-token", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> refreshedTokenUserWhenLogin(@PathVariable("id") Long userId,
                                                              @RequestParam("firebaseToken") String token) {
        User user = userService.getUserById(userId);
        if (user != null) {
            user.setFirebaseToken(token);
        }
        User result = userService.saveUser(user);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
