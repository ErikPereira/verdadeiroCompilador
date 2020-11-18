package Posfixa;


public class analisaPosfixa {

    private String elemento;
    private int nivel;

    public analisaPosfixa(String elemento, int nivel) {
        this.elemento = elemento;
        this.nivel = nivel;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}