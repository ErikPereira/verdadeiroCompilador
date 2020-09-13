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
    
    private String simbolo;
    private String lexema;
    private int linha;
    private boolean erro;
    
    public Token(String simbolo, String lexema, int linha, boolean erro){
        this.simbolo = simbolo;
        this.lexema = lexema;
        this.linha = linha;
        this.erro = erro;
    }
    
    public void setSimbolo(String simbolo){
        this.simbolo = simbolo;
    }
    
    public String getSimbolo(){
        return this.simbolo;
    }
    
    public void setLexema(String lexema){
        this.lexema = lexema;
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
