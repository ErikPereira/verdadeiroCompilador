package Sintatico;

import Lexico.Lexico;
import Lexico.Token;
import Semantico.Semantico;
import compilerException.CompilerException;
import Enum.DescricaoErro;

public class Sintatico {
    private final Lexico lexico;
    private Token token;
    private boolean acabouTokens = false;
    private final Semantico semantico;
    
    public Sintatico(String programa){
        this.lexico = new Lexico(programa);
        this.lexico.analisadorLexical();
        this.semantico = new Semantico();
    }
    
    public void analisadorSintatico() throws CompilerException{
        try{
            lexico();
            if(token.getSimbolo().equals("sprograma")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    lexico();
                    if(token.getSimbolo().equals("sponto_virgula")){
                        analisaBloco();
                        if(token.getSimbolo().equals("sponto")){
                            lexico();
                            if(acabouTokens==true){
                                sucesso();
                            }
                            else  erro("Sintático", DescricaoErro.NAO_ACABOU.getDescricao());
                        }
                        else erro("Sintático", DescricaoErro.FALTA_PONTO_FINAL.getDescricao());
                    }
                    else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
                }
                else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
            }
            else erro("Sintático", DescricaoErro.FALTA_NOME_PROGRAMA.getDescricao());
            
        }catch(CompilerException err){
            throw err;
        }finally{
            lexico.restVariaveis();
        }
    }
    
    private void lexico()throws CompilerException{
        try{
            Token finalizouErrado = token;
            token = lexico.buscaToken();
            if(token == null){
                if(finalizouErrado.getSimbolo().equals("sponto"))
                    acabouTokens = true;
                else{
                    token = finalizouErrado;
                    erro("Sintático", DescricaoErro.FALTA_PONTO_FINAL.getDescricao());
                }
                
            }
            else if(token.getErro()){
                erro("Lexico", DescricaoErro.LEXICO.getDescricao() + " '" + token.getLexema()+ "'");
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaBloco() throws CompilerException{
        try{
            lexico();
            analisaEtVariaveis();
            analisaSubrotinas();
            analisaComandos();
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaEtVariaveis()throws CompilerException{
        try{
            if(token.getSimbolo().equals("svar")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    while(token.getSimbolo().equals("sidentificador")){
                        analisaVariaveis();
                        if(token.getSimbolo().equals("sponto_virgula")) lexico();
                        else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
                    }
                }
                else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaVariaveis()throws CompilerException{
        try{
            do{
                if(token.getSimbolo().equals("sidentificador")){
                    lexico();
                    if(token.getSimbolo().equals("sdoispontos") || token.getSimbolo().equals("svirgula")){
                        if(token.getSimbolo().equals("svirgula")){
                            Token casoErroVirgula = token;
                            lexico();
                            if(token.getSimbolo().equals("sdoispontos")){
                                token = casoErroVirgula;
                                erro("Sintático", DescricaoErro.VIRGULA_ERRADA.getDescricao());
                            }
                        }
                    }
                    else erro("Sintático", DescricaoErro.FALTA_VIRGULA_OU_DOIS_PONTO.getDescricao());
                }
                else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
            }while(!token.getSimbolo().equals("sdoispontos"));
            lexico();
            analisaTipo();
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaTipo()throws CompilerException{
        try{
            if(!token.getSimbolo().equals("sinteiro") && !token.getSimbolo().equals("sbooleano")) {
                erro("Sintático", DescricaoErro.FALTA_TIPO.getDescricao());
            }
            lexico(); 
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaComandos()throws CompilerException{
        try{
            if(token.getSimbolo().equals("sinicio")){
                lexico();
                analisaComandoSimples();
                while(!token.getSimbolo().equals("sfim")){

                    if(token.getSimbolo().equals("sponto_virgula")){
                        lexico();
                        if(!token.getSimbolo().equals("sfim")) analisaComandoSimples();
                    }
                    else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
                }
                lexico();
            }
            else erro("Sintático", DescricaoErro.FALTA_INICIO.getDescricao());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaComandoSimples()throws CompilerException{
        try{
            switch (token.getSimbolo()) {
                case "sidentificador":
                    analisaAtribChprocedimento();
                    break;
                case "sse":
                    analisaSe();
                    break;
                case "senquanto":
                    analisaEnquanto();
                    break;
                case "sleia":
                    analisaLeia();
                    break;
                case "sescreva":
                    analisaEscreva();
                    break;
                default:
                    analisaComandos();
                    break;
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaAtribChprocedimento()throws CompilerException{
        try{
            Token tokenAnterior = token;
            lexico();

            if(token.getSimbolo().equals("satribuicao")) {
                // semantico.pesquisaDeclaraVarTabela(tokenAnterior.getLexema()); // alterei aki
                analisaAtribuicao();
            }
            else analisaChamadaDeProcedimento(tokenAnterior);
        
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaLeia()throws CompilerException{
        try{
            lexico();
            if(token.getSimbolo().equals("sabre_parenteses")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    lexico();
                    if(token.getSimbolo().equals("sfecha_parenteses")){
                        lexico();
                    }
                    else erro("Sintático", DescricaoErro.FALTA_FECHA_PARENTESES.getDescricao());
                }
                else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
            }
            else erro("Sintático", DescricaoErro.FALTA_ABRE_PARENTESES.getDescricao());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaEscreva()throws CompilerException{
        try{
            lexico();
            if(token.getSimbolo().equals("sabre_parenteses")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    lexico();
                    if(token.getSimbolo().equals("sfecha_parenteses")){
                        lexico();
                    }
                    else erro("Sintático", DescricaoErro.FALTA_FECHA_PARENTESES.getDescricao());
                }
                else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
            }
            else erro("Sintático", DescricaoErro.FALTA_ABRE_PARENTESES.getDescricao());
    
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaEnquanto()throws CompilerException{
        try{
            lexico();
            analisaExpressao();
            if(token.getSimbolo().equals("sfaca")){
                lexico();
                analisaComandoSimples();
            }
            else erro("Sintático", DescricaoErro.FALTA_FACA.getDescricao());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaSe()throws CompilerException{
        try{
            lexico();
            analisaExpressao();
            if(token.getSimbolo().equals("sentao")){
                lexico();
                analisaComandoSimples();
                if(token.getSimbolo().equals("ssenao")){
                    lexico();
                    analisaComandoSimples();
                }
            }
            else erro("Sintático", DescricaoErro.FALTA_ENTAO.getDescricao());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaSubrotinas()throws CompilerException{
        try{
            while(token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")){
                
                if(token.getSimbolo().equals("sprocedimento")) analisaDeclaraçãoProcedimento(); 
                else analisaDeclaraçãoFunção();

                if(token.getSimbolo().equals("sponto_virgula")) lexico();
                else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaDeclaraçãoProcedimento()throws CompilerException{
        try{
            lexico();
            if(token.getSimbolo().equals("sidentificador")){
                lexico();
                if(token.getSimbolo().equals("sponto_virgula")){
                    analisaBloco();
                }
                else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
            }
            else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaDeclaraçãoFunção()throws CompilerException{
        try{
            lexico();
            if(token.getSimbolo().equals("sidentificador")){
                lexico();
                if(token.getSimbolo().equals("sdoispontos")){
                    lexico();
                    if(token.getSimbolo().equals("sinteiro") || token.getSimbolo().equals("sbooleano")){
                        lexico();
                        if(token.getSimbolo().equals("sponto_virgula")){
                            analisaBloco();
                        }
                    }
                    else erro("Sintático", DescricaoErro.FALTA_TIPO.getDescricao());
                }
                else erro("Sintático", DescricaoErro.FALTA_DOIS_PONTOS.getDescricao());
            }
            else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaExpressao()throws CompilerException{
        try{
            analisaExpressãoSimples();
            switch(token.getSimbolo()){
                case "smaior":
                case "smenor":
                case "smaiorig":
                case "sig":
                case "smenorig":
                case "sdif":
                    lexico();
                    analisaExpressãoSimples();
                    break;
            }            
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaExpressãoSimples()throws CompilerException{
        try{
            if(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")) lexico();            
            analisaTermo();
            while(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos") || token.getSimbolo().equals("sou")){
                lexico();
                analisaTermo();            
            }     
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaTermo()throws CompilerException{
        try{
            analisaFator();
            while(token.getSimbolo().equals("smult") || token.getSimbolo().equals("sdiv") || token.getSimbolo().equals("se")){
                lexico();
                analisaFator();       
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaFator()throws CompilerException{
        try{
            if(token.getSimbolo().equals("sidentificador")){
                analisaChamadaDeFuncao();
                ////////
            }
            else if(token.getSimbolo().equals("snumero")) lexico();
            else if(token.getSimbolo().equals("snao")){
                lexico();
                analisaFator();
            }    
            else if(token.getSimbolo().equals("sabre_parenteses")){
                lexico();
                analisaExpressao();
                if(token.getSimbolo().equals("sfecha_parenteses")){
                    lexico();
                }
                else erro("Sintático", DescricaoErro.FALTA_FECHA_PARENTESES.getDescricao());
            }

            else if(token.getLexema().equals("verdadeiro") || token.getLexema().equals("falso")) lexico();
            else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaChamadaDeProcedimento(Token tokenAnterior)throws CompilerException{
        /* try{
            semantico.pesquisaDeclaraProcedimentoTabela(tokenAnterior.getLexema()); // alterei aki
        }catch(CompilerException err){
            throw err;
        }*/
    }
    
    private void analisaChamadaDeFuncao()throws CompilerException{
        try{
            // semantico.pesquisaDeclaraFuncaoTabela(token.getLexema());
            lexico();
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaAtribuicao()throws CompilerException{
        try{
            lexico();
            analisaExpressao();
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void erro(String etapaErro, String descricao) throws CompilerException{
        throw new CompilerException(token.getLinha(), etapaErro, descricao);
    }
    
    private void sucesso(){
        // deu tudo certo
    }
}
