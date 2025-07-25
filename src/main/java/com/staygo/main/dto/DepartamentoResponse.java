package com.staygo.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoResponse {
    private Integer id;
    private String nombre;
    private String dueno;
    private String direccion;
    private String descripcion;
    private String imagen;
    private float precio;
    private int numHabitaciones;
}
