package sv.ues.fia.eisi.pdm115proyecto2;

public class Contenidos {

    private String contenido;
    private String nombre;

    public Contenidos() {
    }

    public Contenidos(String contenido, String nombre) {
        this.contenido = contenido;
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
