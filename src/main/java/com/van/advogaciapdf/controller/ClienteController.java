package com.van.advogaciapdf.controller;

import com.van.advogaciapdf.dto.EmailDetailsDto;
import com.van.advogaciapdf.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class ClienteController {


    private final EmailService emailService;


    @PostMapping
    public ResponseEntity<?> mandarEmail(@RequestBody EmailDetailsDto emailDetailsDto) throws Exception {

        emailService.mandarEmail(emailDetailsDto);

        return ResponseEntity.ok("mandado email");

    }


}
