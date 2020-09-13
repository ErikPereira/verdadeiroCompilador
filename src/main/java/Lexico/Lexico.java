/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.JTextArea;

public class Lexico {

    private String programa;
    private int posicaoPrograma;
    private int linha;
    private List<Token> tokens;
    private boolean naoAcabouArquivo = true; 
   
    public Lexico(String programa) {
        this.programa = programa;
        this.linha = 1;
        this.posicaoPrograma = 0;
        this.tokens = new ArrayList<>();
    }
    
    public void analisadorLexical(){
        try{
            char caracter = ler();
            
            while(naoAcabouArquivo){ //pensar em algo para tirar esta merda daki!!!!!
                switch(caracter){
                    
                    case '{':
                        pegaToken(String.valueOf(caracter));
                        caracter = ler();
                        while(caracter != '}')
                            caracter = ler();
                        pegaToken(String.valueOf(caracter));
                        break;
                        
                    case '/':
                        caracter = ler();
                        if(caracter == '*'){
                            pegaToken("/*");
                            do{
                                while(caracter != '*')
                                    caracter = ler();  
                                caracter = ler(); 
                            }while(caracter!= '/');
                            pegaToken("*/");
                        }
                        else
                            erro("/");
                        break;
                        
                    case ' ':
                    case '\t':
                    case '\n':
                        break;
                        
                    default:
                        pegaToken(String.valueOf(caracter));
                        break;      
                }
                caracter = ler();
            } 
        }catch(Exception err){
            JOptionPane.showMessageDialog(null,getListaTokens());
            // retornar lista de tokens
            // printar lista de tokens em um JOptionPane
        }finally{
            restVariaveis();
        }
    }
    
    private void pegaToken(String caracter) throws Exception{
        try{
            if( caracter.matches("^[0-9]*$")){
                tratarDigito(caracter);
            }
            else if(caracter.matches("^[a-zA-Z]*$")){
                tratarIdentificadorEPalavraReservada(caracter);
            }
            else{
                switch(caracter){
                    case ":":
                        tratarAtribuicao(caracter);
                        break;

                    case "+":
                    case "-":
                    case "*":
                        tratarOperadorAritmetico(caracter);
                        break;

                    case ">":
                    case "<":
                    case "=":
                        tratarOperadorRelacional(caracter);
                        break;

                    case ";":
                    case ",":
                    case "(":
                    case ")":
                    case ".":
                        tratarPontuacao(caracter);
                        break;

                    case "{":
                    case "}":
                    case "/*":
                    case "*/":
                        tratarComentario(caracter);
                        break;

                    default:
                        erro(caracter);
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
            naoAcabouArquivo = false;
            throw err;
        }      
        char caracter = programa.charAt(posicaoPrograma++);
        if(caracter == '\n'){
            linha++;
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
    
    private void tratarOperadorRelacional(String caracter)throws Exception{
        try {
            JOptionPane.showMessageDialog(null,"Sou um OperadorRelacional = " + caracter);
            switch (caracter) {
                case "=":
                    setTokens("=", "sig", false);
                    break;
                case ">":
                    caracter = String.valueOf(ler());
                    if(caracter.equals("="))
                        setTokens(">=", "smaiorig", false);
                    else{
                        setTokens(">", "smaior", false);
                        posicaoPrograma--;
                    }
                    break;
                case "<":
                    caracter = String.valueOf(ler());
                    if(caracter.equals("="))
                        setTokens("<=", "smenorig", false);
                    else{
                        setTokens("<", "smenor", false);
                        posicaoPrograma--;
                    }
                    break;
                default:
                    erro(caracter);
                    break;
            }
        } catch (Exception err) {
            throw err;
        }
        
    }
    
    private void tratarPontuacao(String caracter) throws Exception{
        try {
            JOptionPane.showMessageDialog(null,"Sou uma Pontuacao = " + caracter);
            String lexema = "";
            switch (caracter) {
                case ";":
                    lexema = "sponto_virgula";
                    break;
                case ",":
                    lexema = "svirgula";
                    break;
                case "(":
                    lexema = "sabre_parenteses";
                    break;
                case ")":
                    lexema = "sfecha_parenteses";
                    break;
                case ".":
                    lexema = "sponto";
                    break;
                default:
                    erro(caracter);
                    break;
            }
            setTokens(caracter, lexema, false);
        } catch (Exception err) {
            throw err;
        }
    }
    
    private void tratarComentario(String caracter) throws Exception{
         try {
            JOptionPane.showMessageDialog(null,"Sou um Comentario = " + caracter);
            String lexema = "";

            switch (caracter) {
                case "{":
                    lexema = "sabre_chaves";
                    break;
                case "}":
                    lexema = "sfecha_chaves";
                    break;
                case "/*":
                    lexema = "sabre_barra_asterisco";
                    break;
                case "*/":
                    lexema = "sfecha_asterisco_barra";
                    break;
                default:
                    erro(caracter);
                    break;
            }
            setTokens(caracter, lexema, false);
        } catch (Exception err) {
            throw err;
        }
    }
    
    private void erro(String caracter) throws Exception{
        JOptionPane.showMessageDialog(null,"Sou um erro = " + caracter);
        Exception err = null;
        setTokens(caracter, "", true);
        throw err;
    } 
    
    private void setTokens(String simbolo, String lexema, boolean erro){
        tokens.add(new Token(simbolo, lexema, linha, erro));
    }
    
    public String getListaTokens(){
        String lista = "Simbolo\tLexema\tLinha\n";
        
        for(Token token : tokens){
            if(!token.getErro())
                lista = lista.concat(token.getSimbolo() 
                    + "\t" + token.getLexema()
                    + "\t" + token.getLinha()
                );
            else
                lista = lista.concat("\nErro:\nSimbolo\tlinha"
                           + token.getSimbolo() 
                    + "\t" + token.getLinha()
                );
        }
        return lista;
    }
  
    private void restVariaveis(){
        linha = 1;
        posicaoPrograma = 0;
        tokens.clear();
    }
    
    
    
}
