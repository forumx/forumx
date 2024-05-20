package com.example.forumx.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@RestController
public class HelloApi {
    @GetMapping("/hello")
    public ResponseEntity<String> hello() throws UnknownHostException {
        String ip = "";
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
             ip = socket.getLocalAddress().getHostAddress();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Hello ip: " + ip);
    }
}
