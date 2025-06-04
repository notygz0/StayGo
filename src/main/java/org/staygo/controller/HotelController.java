package org.staygo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hoteles")
public class HotelController {

    @GetMapping
    public String listarHoteles() {
        return "hoteles";
    }
}
