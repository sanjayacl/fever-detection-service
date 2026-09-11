package com.sanja.feverdetection.controller;

import com.sanja.feverdetection.dto.FeverResponse;
import com.sanja.feverdetection.service.FeverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fever")
public class FeverController {

    private static final Logger log = LoggerFactory.getLogger(FeverController.class);

    private final FeverService feverService;

    public FeverController(FeverService feverService) {
        this.feverService = feverService;
    }

    @GetMapping("/check")
    public FeverResponse checkFever(@RequestParam double celsius) {
        log.info("Received fever check request for celsius={}", celsius);
        FeverResponse response = feverService.checkFever(celsius);
        log.info("Fever check result for celsius={}: fahrenheit={}, fever={}",
                celsius, response.getFahrenheit(), response.isFever());
        return response;
    }
}
