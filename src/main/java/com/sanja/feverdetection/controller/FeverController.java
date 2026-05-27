package com.sanja.feverdetection.controller;

import com.sanja.feverdetection.dto.FeverResponse;
import com.sanja.feverdetection.service.FeverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fever")
public class FeverController {

    private final FeverService feverService;

    public FeverController(FeverService feverService) {
        this.feverService = feverService;
    }

    @GetMapping("/check")
    public FeverResponse checkFever(@RequestParam double celsius) {
        return feverService.checkFever(celsius);
    }
}
