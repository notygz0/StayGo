package org.staygo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hoteles")
public class HotelController {

    @GetMapping
    public String listarHoteles() {
        return "hoteles";
    }
}
