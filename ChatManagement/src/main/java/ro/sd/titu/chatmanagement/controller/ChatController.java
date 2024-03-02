package ro.sd.titu.chatmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sd.titu.chatmanagement.entity.Message;

import java.util.List;

public interface ChatController {
    @PostMapping()
    ResponseEntity<Void> saveMessage(@RequestHeader("Authorization") String token, @RequestBody Message message);

    @GetMapping("{id1}/{id2}")
    ResponseEntity<List<Message>> getMessage(@RequestHeader("Authorization") String token, @PathVariable("id1")String id1, @PathVariable("id2")String id2);
}
