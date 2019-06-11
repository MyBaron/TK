package com.mune.system.controller;


import com.mune.system.websocket.WebSocket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/socket")
    public void socket(String msg) {
        WebSocket.sendAllMessage(msg);
    }
}
