package com.staygo.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {
    private Integer id;
    private String name;
    private String imagen;
    private String alojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
}