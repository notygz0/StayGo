package org.staygo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoRequest {
    private String nombre;
    private String descripcion;
    private float precio;
    private int numHabitaciones;
}
