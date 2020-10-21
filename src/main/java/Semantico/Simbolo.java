package Semantico;

public class Simbolo {
    private String tipoLexema;
    private String lexema;
    private String rotulo;
    private String tipo;
    private int nivel;
    
    public Simbolo(String tipoLexema, String lexema, String rotulo, int nivel){
        this.tipoLexema = tipoLexema;
        this.lexema = lexema;
        this.rotulo = rotulo;
        this.nivel = nivel;
    }

    public void setTipoLexema(String tipoLexema) {
        this.tipoLexema = tipoLexema;
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
        return tipoLexema;
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
