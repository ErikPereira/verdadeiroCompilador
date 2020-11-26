package Lexico;

import java.util.ArrayList;
import java.util.List;

public class Lexico {
    private final List<Token> tokens;
    private final String programa;
    private int posicaoPrograma;
    private int linha;
    private int proximoToken;
    private boolean naoAcabouArquivo = true;
        
    public Lexico(String programa) {
        this.programa = programa;
        this.linha = 1;
        this.proximoToken = 0;
        this.posicaoPrograma = 0;
        this.tokens = new ArrayList<>();
    }
    
    public void analisadorLexical(){
        boolean fechoComentario = true;
        int linhaComentario = 0;
        String qualComentario = "";
        try{
            char caracter = ler();
            
            while(naoAcabouArquivo){
                switch(caracter){
                    
                    case '{':
                        fechoComentario = false;
                        linhaComentario = this.linha;
                        qualComentario = "{";
                        caracter = ler();
                        
                        while(caracter != '}')
                            caracter = ler();
                        
                        fechoComentario = true;
                        break;
                        
                    case '/':
                        caracter = ler();
                        if(caracter == '*'){
                            fechoComentario = false;
                            linhaComentario = this.linha;
                            qualComentario = "/*";
                            do{
                                while(caracter != '*')
                                    caracter = ler();  
                                caracter = ler(); 
                            }while(caracter!= '/');
                            fechoComentario = true;
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
            if(!fechoComentario){
                tokens.add(new Token("Comentario não fechado", qualComentario, linhaComentario, true));
            }
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
                    case "!":
                        tratarOperadorRelacional(caracter);
                        break;

                    case ";":
                    case ",":
                    case "(":
                    case ")":
                    case ".":
                        tratarPontuacao(caracter);
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
            decrementarPosicaoPrograma(String.valueOf(aux));
            setTokens("snumero",num,false);
            
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
            while(straux.matches("^[a-zA-Z]$") || straux.matches("^[0-9]$") || straux.equals("_") || straux.equals("í")){
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
                case "inicio":
                    tipo = "sinicio";
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
            decrementarPosicaoPrograma(String.valueOf(aux));
            setTokens(tipo,id,false);
            
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
                decrementarPosicaoPrograma(String.valueOf(aux));
                setTokens("sdoispontos",caracter, false);
               
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
                case "!":
                    caracter = String.valueOf(ler());
                    if(caracter.equals("="))
                        setTokens("sdif", "!=",  false);
                    else{
                        erro("!");
                    }
                    break;
                case ">":
                    caracter = String.valueOf(ler());
                    if(caracter.equals("="))
                        setTokens("smaiorig", ">=",  false);
                    else{
                        decrementarPosicaoPrograma(caracter);
                        setTokens("smaior", ">",  false);  
                    }
                    break;
                case "<":
                    caracter = String.valueOf(ler());
                    if(caracter.equals("="))
                        setTokens("smenorig", "<=", false);
                    else{
                        decrementarPosicaoPrograma(caracter);
                        setTokens("smenor", "<", false);
                        
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

    private void erro(String caracter) throws Exception{
        Exception err = null;
        setTokens("NÃO ENCONTRADO", caracter, true);
        throw err;
    } 
    
    private void setTokens(String simbolo, String lexema, boolean erro){
        tokens.add(new Token(simbolo, lexema, linha, erro));
    }
    
    public String getListaTokens(){
        String lista = "Lexema | Simbolo | Linha | Erro\n\n";

        for(Token token : tokens){
            lista = lista.concat( token.getLexema()  + " | "
                                + token.getSimbolo() + " | "
                                + token.getLinha()   + " | "
                                + token.getErro()    + "\n"
            );
        }
        return lista;
    }
    
    public void resetVariaveis(){
        linha = 1;
        posicaoPrograma = 0;
        tokens.clear();
    }
    
    private void decrementarPosicaoPrograma(String caracter){
        if(caracter.equals("\n")){
            linha --;
        }
        posicaoPrograma--;
    }
    
    public Token buscaToken(){
        if(proximoToken >= tokens.size()){
            return null;
        }
        return tokens.get(proximoToken++);
    }
}
