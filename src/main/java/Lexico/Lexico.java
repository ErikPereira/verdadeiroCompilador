/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class Lexico {

    private String programa;
    private int posicaoPrograma;
    private int linha;
    private List<Token> tokens;
    private boolean naoAcabouArquivo = true; 
   
    public Lexico(String programa) {
        this.programa = programa;
        this.linha = 0;
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
            JOptionPane.showMessageDialog(null,getListaTokens(), "Lita de Tokens!", JOptionPane.INFORMATION_MESSAGE);
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
        }
        return caracter;
    }

    private void tratarDigito(String caracter) throws Exception{
        try {
            String num = caracter;
            char aux = this.ler();
            
            while(String.valueOf(aux).matches("^[0-9]*$")){
                num = num + aux;
                aux = this.ler();
            }
            setTokens("snumero",num,false);
            decrementarPosicaoPrograma(caracter);
        } catch (Exception err) {
            throw err;
        }
    }
    
    private void tratarIdentificadorEPalavraReservada(String caracter) throws Exception{
        try {
            String tipo = "sidentificador";
            String id = caracter;
            char aux = this.ler();
            String straux = String.valueOf(aux);
            while(straux.matches("^[a-zA-Z]$") || straux.matches("^[0-9]$") || straux.equals('_')){
                id = id + straux;
                aux = this.ler();
                straux = String.valueOf(aux);
            }
            
            switch(id){
                case "programa":
                    tipo = "sprograma";
                    break;
                case "se":
                    tipo = "sse";
                    break;
                case "entao":
                    tipo = "sentao";
                    break;
                case "senao":
                    tipo = "ssenao";
                    break;
                case "enquanto":
                    tipo = "senquanto";
                    break;
                case "faca":
                    tipo = "sfaca";
                    break;
                case "início":
                    tipo = "sinício";
                    break;
                case "fim":
                    tipo = "sfim";
                    break;
                case "escreva":
                    tipo = "sescreva";
                    break;
                case "leia":
                    tipo = "sleia";
                    break;
                case "var":
                    tipo = "svar";
                    break;
                case "inteiro":
                    tipo = "sinteiro";
                    break;
                case "booleano":
                    tipo = "sbooleano";
                    break;
                case "verdadeiro":
                    tipo = "sverdadeiro";
                    break;
                case "falso":
                    tipo = "sfalso";
                    break;
                case "procedimento":
                    tipo = "sprocedimento";
                    break;
                case "funcao":
                    tipo = "sfuncao";
                    break;
                case "div":
                    tipo = "sdiv";
                    break;
                case "e":
                    tipo = "se";
                    break;
                case "ou":
                    tipo = "sou";
                    break;
                case "nao":
                    tipo = "snao";
                    break;
                default:
                    break;
            }
            setTokens(tipo,id,false);
            decrementarPosicaoPrograma(String.valueOf(aux));
        } catch (Exception err) {
            throw err;
        }
    }
    
    private void tratarAtribuicao(String caracter) throws Exception{
        try {
            char aux = ler();
            if(aux == '='){
                setTokens("satribuicao",":=", false);
            }
            else{
                setTokens("sdoispontos",caracter, false);
                decrementarPosicaoPrograma(String.valueOf(aux));
            } 
        } catch (Exception err) {
            throw err;
        }
    }
    
    private void tratarOperadorAritmetico(String caracter){
        String tipo = null;
        switch(caracter){
            case "+":
                tipo = "smais";
                break;
            case "-":
                tipo = "smenos";
                break;
            case "*":
                tipo = "smult";
                break;
        }
        setTokens(tipo,caracter, false);
    }
    
    private void tratarOperadorRelacional(String caracter)throws Exception{
        try {    
            switch (caracter) {
                case "=":
                    setTokens("sig", "=", false);
                    break;
                case ">":
                    caracter = String.valueOf(ler());
                    if(caracter.equals("="))
                        setTokens("smaiorig", ">=",  false);
                    else{
                        setTokens("smaior", ">",  false);
                        decrementarPosicaoPrograma(caracter);
                    }
                    break;
                case "<":
                    caracter = String.valueOf(ler());
                    if(caracter.equals("="))
                        setTokens("smenorig", "<=", false);
                    else{
                        setTokens("smenor", "<", false);
                        decrementarPosicaoPrograma(caracter);
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
            setTokens(lexema, caracter, false);
        } catch (Exception err) {
            throw err;
        }
    }
    
    private void tratarComentario(String caracter) throws Exception{
         try {        
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
            setTokens(lexema, caracter, false);
        } catch (Exception err) {
            throw err;
        }
    }
    
    private void erro(String caracter) throws Exception{
        Exception err = null;
        setTokens("", caracter, true);
        throw err;
    } 
    
    private void setTokens(String simbolo, String lexema, boolean erro){
        tokens.add(new Token(simbolo, lexema, linha, erro));
    }
    
    public String getListaTokens(){
        List<String> bgcolor = new ArrayList<>();
        String lista = "<html>"
            + "<table align='center' border='1' cellpadding='0' cellspacing='0' color='black'>"
            + "<thead> <tr bgcolor='#000000' style='color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;' height= '45px'>"
            + "<th width='80px'>Lexema</th>" 
            + "<th width='180px' >Simbolo</th>"
            + "<th width='70px' >Linha</th>"
            + "<th width='70px'>Erro</th>"
            + "</tr> </thead> <tbody>";
        int count = 0;
        bgcolor.add("bgcolor='#ffffff'");
        bgcolor.add("bgcolor='#e0e0e0'");
        
        for(Token token : tokens){
            if(!token.getErro())
                lista = lista.concat("<tr align='center' style='color: #000000; font-family: Arial, sans-serif; font-size: 12px;' "+ bgcolor.get(count%2)+">"
                    + "<td>" + token.getLexema() + "</td>"
                    + "<td>" + token.getSimbolo()  + "</td>"
                    + "<td>" + token.getLinha()   + "</td>"
                    + "<td>" + token.getErro()    + "</td></tr>"
                );
            else
                lista = lista.concat("<tr align='center' style='color: #ff0000; font-family: Arial, sans-serif; font-size: 14px;' "+ bgcolor.get(count%2) +">"
                    + "<td>" + token.getLexema() + "</td>"
                    + "<td>" + "NÃO ENCONTRADO"  + "</td>"
                    + "<td>" + token.getLinha()   + "</td>"
                    + "<td>" + token.getErro()    + "</td></tr>"
                );
            count++;
        }
        lista = lista.concat("</tbody></table></html>");
        return lista;
    }
  
    private void restVariaveis(){
        linha = 1;
        posicaoPrograma = 0;
        tokens.clear();
    }
    
    private void decrementarPosicaoPrograma(String caracter){
        if(!caracter.equals("\n")){
            posicaoPrograma--;
        }
    }
    
}
