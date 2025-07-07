package com.staygo.main.controller;

import com.staygo.main.repository.HotelRepository;
import com.staygo.main.servicio.HotelServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hoteles")
@RequiredArgsConstructor
public class HotelController {
    private final HotelServicio hotelServicio;
    @GetMapping
    public String Hoteles() {
        return "hoteles";
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listarHoteles() {
        return hotelServicio.listarhotels();
    }


}