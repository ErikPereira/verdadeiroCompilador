/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

/**
 *
 * @author Erik Silva
 */
public class Token {
    
    private String simbulo;
    private String lexema;
    private int linha;
    private boolean erro;
    
    public Token(){

    }
    
    public void setSimbulo(String simbulo){
        this.simbulo = simbulo;
    }
    
    public String getSimbulo(){
        return this.simbulo;
    }
    
    public void setLexema(String lexema){
        this.simbulo = lexema;
    }
    
    public String getLexema(){
        return this.lexema;
    }
    
    public void setLinha(int linha){
        this.linha = linha;
    }
    
    public int getLinha(){
        return this.linha;
    }
    
    public void setErro(boolean erro){
        this.erro = erro;
    }
    
    public boolean getErro(){
        return this.erro;
    }
    
}
