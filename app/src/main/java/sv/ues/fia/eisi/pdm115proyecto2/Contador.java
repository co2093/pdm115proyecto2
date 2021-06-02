package sv.ues.fia.eisi.pdm115proyecto2;

public class Contador {

    private String nombreC;
    private int contador;

    public Contador() {
    }

    public Contador(String nombreC, int contador) {
        this.nombreC = nombreC;
        this.contador = contador;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
