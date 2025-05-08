package org.staygo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * clase que gestiona la carga y el almacenamiento de datos de alojamientos y usuarios desde/hacia archivos JSON.
 * utiliza la libreria jackson para convertir los objetos a formato JSON y viceversa.
 *
 * @author Lorenzo Lopez
 */
public class GestorDeDatos {

    private static final String ALOJAMIENTOS_FILE = "alojamientos.json";
    private static final String USUARIOS_FILE = "usuarios.json";
    private ObjectMapper objectMapper;

    public GestorDeDatos() {
        this.objectMapper = new ObjectMapper();
    }


    public List<Alojamiento> cargarAlojamientos() {
        try {
            File file = new File(ALOJAMIENTOS_FILE);
            if (file.exists()) {
                return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Alojamiento.class));
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Usuario> cargarUsuarios() {
        try {
            File file = new File(USUARIOS_FILE);
            if (file.exists()) {
                return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Usuario.class));
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public void guardarAlojamientos(List<Alojamiento> alojamientos) {
        try {
            objectMapper.writeValue(new File(ALOJAMIENTOS_FILE), alojamientos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void guardarUsuarios(List<Usuario> usuarios) {
        try {
            objectMapper.writeValue(new File(USUARIOS_FILE), usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

