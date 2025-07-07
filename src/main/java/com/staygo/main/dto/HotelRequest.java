package com.staygo.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {
    private String nombre;
    private String direccion;
    private String descripcion;
    private float precio;
    private int NumEstrellas;
    private int numHabitaciones;
    private MultipartFile imagen;
}