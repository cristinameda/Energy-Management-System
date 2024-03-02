package ro.sd.titu.chatmanagement.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sd.titu.chatmanagement.controller.ChatController;
import ro.sd.titu.chatmanagement.entity.Message;
import ro.sd.titu.chatmanagement.security.config.JwtService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/chat")
@CrossOrigin("*")
public class ChatControllerImpl implements ChatController {

    private final List<Message> messages = new ArrayList<>();
    private final JwtService jwtService;

    public ChatControllerImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<Void> saveMessage(String token, Message message) {
        if (isAuthorized(token)) {
            messages.add(message);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<List<Message>> getMessage(String token, String id1, String id2) {
        if (isAuthorized(token)) {
            List<Message> directMessages = new ArrayList<>();
            for (Message message : messages) {
                if (message.getFrom().equals(id1) && message.getTo().equals(id2)
                        || message.getFrom().equals(id2) && message.getTo().equals(id1)) {
                    directMessages.add(message);
                }
            }
            return new ResponseEntity<>(directMessages, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private boolean isAuthorized(String token) {
        return jwtService.isAuthorizeBasedOnRole(token);
    }
}
