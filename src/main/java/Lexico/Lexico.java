/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;

public class Lexico {

    private String programa;
    private int posicaoPrograma;
    private int linha;
    private List<Token> tokens;
   
    public Lexico(String programa) {
        this.programa = programa;
        this.linha = 1;
        this.posicaoPrograma = 0;
        this.tokens = new ArrayList<>();
    }
    
    public void analisadorLexical(){
        try{
            char caracter = this.ler();
            
            while(true){ //pensar em algo para tirar esta merda daki!!!!!
                switch(caracter){
                    
                    case '{':
                        this.pegaToken(String.valueOf(caracter));
                        caracter = this.ler();
                        while(caracter != '}')
                            caracter = this.ler();
                        this.pegaToken(String.valueOf(caracter));
                        break;
                        
                    case '/':
                        caracter = this.ler();
                        if(caracter == '*'){
                            this.pegaToken("/*");
                            do{
                                while(caracter != '*')
                                    caracter = this.ler();  
                                caracter = this.ler(); 
                            }while(caracter!= '/');
                            this.pegaToken("*/");
                        }
                        else
                            this.erro("/");
                        break;
                        
                    case ' ':
                    case '\t':
                    case '\n':
                        break;
                        
                    default:
                        this.pegaToken(String.valueOf(caracter));
                        break;      
                }
                caracter = this.ler();
            } 
        }catch(Exception err){
            JOptionPane.showMessageDialog(null,this.getListaTokens());
            // retornar lista de tokens
            // printar lista de tokens em um JOptionPane
        }finally{
            this.restVariaveis();
        }
    }
    
    private void pegaToken(String caracter) throws Exception{
        try{
            if( caracter.matches("^[0-9]*$")){
                this.tratarDigito(caracter);
            }
            else if(caracter.matches("^[a-zA-Z]*$")){
                this.tratarIdentificadorEPalavraReservada(caracter);
            }
            else{
                switch(caracter){
                    case ":":
                        this.tratarAtribuicao(caracter);
                        break;

                    case "+":
                    case "-":
                    case "*":
                        this.tratarOperadorAritmetico(caracter);
                        break;

                    case ">":
                    case "<":
                    case "=":
                        this.tratarOperadorRelacional(caracter);
                        break;

                    case ";":
                    case ",":
                    case "(":
                    case ")":
                    case ".":
                        this.tratarPontuacao(caracter);
                        break;

                    case "{":
                    case "}":
                    case "/*":
                    case "*/":
                        this.tratarComentario(caracter);
                        break;

                    default:
                        this.erro(caracter);
                        break;
                }
            }
        }catch(Exception err){
            throw err;
        }   
    }
    
    private char ler() throws Exception{
        if(posicaoPrograma == programa.length()){
            Exception err = null;
            throw err;
        }      
        char caracter = this.programa.charAt(posicaoPrograma++);
        if(caracter == '\n'){
            this.linha++;
            caracter = ' ';
        }
        return caracter;
    }

    private void tratarDigito(String caracter){
        JOptionPane.showMessageDialog(null,"Sou o numero = " + caracter);
    }
    
    private void tratarIdentificadorEPalavraReservada(String caracter){
        JOptionPane.showMessageDialog(null,"Sou a letra = " + caracter);
    }
    
    private void tratarAtribuicao(String caracter){
        JOptionPane.showMessageDialog(null,"Sou uma atribuição = " + caracter);
    }
    
    private void tratarOperadorAritmetico(String caracter){
        JOptionPane.showMessageDialog(null,"Sou um OperadorAritmetico = " + caracter);
    }
    
    private void tratarOperadorRelacional(String caracter){
        JOptionPane.showMessageDialog(null,"Sou um OperadorRelacional = " + caracter);
    }
    
    private void tratarPontuacao(String caracter){
        JOptionPane.showMessageDialog(null,"Sou uma Pontuacao = " + caracter);
    }
    
    private void tratarComentario(String caracter){
        JOptionPane.showMessageDialog(null,"Sou um Comentario = " + caracter);
    }
    
    private void erro(String caracter) throws Exception{
        Exception err = null;
        JOptionPane.showMessageDialog(null,"Sou um erro = " + caracter);
        throw err;
    } 
    
    private void setTokens(String simbolo, String lexema, int linha, boolean erro){
        this.tokens.add(new Token(simbolo, lexema, linha, erro));
    }
    
    public String getListaTokens(){
        return "passei pelo erro";
    }
  
    private void restVariaveis(){
        this.linha = 1;
        this.posicaoPrograma = 0;
        this.tokens.clear();
    }
    
    
    
}
