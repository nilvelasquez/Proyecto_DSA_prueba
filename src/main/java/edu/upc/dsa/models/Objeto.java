package edu.upc.dsa.models;

public class Objeto {

    String nombre;

    String descripcion;

    double precio;

    String fotoImagen;

    public Objeto() {
    }

    public Objeto(String nombre, String descripcion, double precio, String fotoImagen){
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setPrecio(precio);
        this.setFotoImagen(fotoImagen);
    }
    public String toString(){
        return getNombre();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFotoImagen() {
        return fotoImagen;
    }

    public void setFotoImagen(String fotoImagen) {
        this.fotoImagen = fotoImagen;
    }
}
