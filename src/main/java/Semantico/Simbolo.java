package Semantico;

public class Simbolo {
    private String tipoIdentificado;
    private String lexema;
    private String rotulo;
    private String tipo;
    private int nivel;
    
    public Simbolo(String tipoIdentificado, String lexema, String rotulo, int nivel){
        this.tipoIdentificado = tipoIdentificado;
        this.lexema = lexema;
        this.rotulo = rotulo;
        this.nivel = nivel;
        this.tipo = "";
    }

    public void setTipoLexema(String tipoIdentificado) {
        this.tipoIdentificado = tipoIdentificado;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTipoLexema() {
        return tipoIdentificado;
    }

    public String getLexema() {
        return lexema;
    }

    public String getRotulo() {
        return rotulo;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }
    
    
}
