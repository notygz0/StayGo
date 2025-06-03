package org.staygo.entity;

/**
 * clase abstracta que representa un alojamiento en el sistema.
 * los alojamientos pueden ser hoteles o departamentos.
 * contiene atributos comunes para todos los tipos de alojamientos, como direccion,
 * precio, descripcion y estado de ocupacion.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */
public abstract class Alojamiento {

    private String direccion;
    private float precio;
    private String descripcion;
    private boolean ocupado;

    /**
     * constructor por defecto para crear una instancia de alojamiento.
     * este constructor no asigna valores a los atributos.
     */
    protected Alojamiento() {
    }

    /**
     * constructor para crear una nueva instancia de alojamiento con valores iniciales.
     *
     * @param direccion la direccion del alojamiento.
     * @param precio el precio del alojamiento por noche.
     * @param descripcion una descripcion del alojamiento.
     */
    protected Alojamiento(String direccion, float precio, String descripcion) {
        this.direccion = direccion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.ocupado = false;
    }

    /**
     * obtiene la direccion del alojamiento.
     *
     * @return la direccion del alojamiento.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * obtiene el precio del alojamiento.
     *
     * @return el precio del alojamiento.
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * obtiene la descripcion del alojamiento.
     *
     * @return la descripcion del alojamiento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * obtiene el estado de ocupacion del alojamiento.
     *
     * @return true si el alojamiento esta ocupado, false si esta libre.
     */
    public boolean isOcupado() {
        return ocupado;
    }

    /**
     * establece el estado de ocupacion del alojamiento.
     *
     * @param ocupado el estado de ocupacion a establecer.
     */
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    /**
     * metodo abstracto para obtener los detalles del alojamiento.
     * cada tipo de alojamiento debe implementar este metodo.
     *
     * @return una cadena con los detalles del alojamiento.
     */
    public abstract String verDetalles();

    /**
     * metodo que devuelve el dueno del alojamiento.
     * este metodo es implementado en las subclases, pero por defecto
     * retorna null en esta clase abstracta.
     *
     * @return el dueno del alojamiento o null.
     */
    public Usuario getDueno() {
        return null;
    }
}


