package Semantico;

public class Simbolo {
    private String tipoLexema;
    private String lexema;
    private String rotulo;
    private String tipo;
    private int nivel;
    private String posicaoMemoria;
    
    public Simbolo(String tipoIdentificado, String lexema, String rotulo, int nivel, String posicaoMemoria){
        this.tipoLexema = tipoIdentificado;
        this.lexema = lexema;
        this.rotulo = rotulo;
        this.nivel = nivel;
        this.tipo = "";
        this.posicaoMemoria = posicaoMemoria;
    }   

    public void setTipoLexema(String tipoIdentificado) {
        this.tipoLexema = tipoIdentificado;
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

    public void setPosicaoMemoria(String posicaoMemoria) {
        this.posicaoMemoria = posicaoMemoria;
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

    public String getPosicaoMemoria() {
        return posicaoMemoria;
    }
    
}
