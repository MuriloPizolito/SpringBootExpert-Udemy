package br.com.murilo.resourceserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint(){
        return ResponseEntity.ok("PUBLIC ENDPOINT OK!");
    }

    @GetMapping("/private")
    private ResponseEntity<String> privateEndpoint(){
        return ResponseEntity.ok("PRIVATE ENDPOINT OK!");
    }

}
