package Sintatico;

import Lexico.Lexico;
import Lexico.Token;
import Semantico.Semantico;
import compilerException.CompilerException;
import Enum.DescricaoErro;
import GeracaoDeCodigo.GeracaoDeCodigo;

public class Sintatico {
    private final Lexico lexico;
    private final Semantico semantico;
    private final GeracaoDeCodigo geracaoDeCodigo;
    private Token token;
    private Token tokenAnterior;
    private boolean acabouTokens;
    private String tipoVariavelExpressao = "null";
    private String verdadeiroSosinho = "null";
    private int rotulo;
    
    public Sintatico(String programa){
        this.lexico = new Lexico(programa);
        this.lexico.analisadorLexical();
        this.semantico = new Semantico();
        this.token = new Token("", "", 0, false);
        this.geracaoDeCodigo = new GeracaoDeCodigo();
        this.rotulo = 0;
        this.acabouTokens = false;
        this.tipoVariavelExpressao = "null";
        this.verdadeiroSosinho = "null";
    }
    
    public void analisadorSintatico() throws CompilerException{
        try{
            lexico();
            if(token.getSimbolo().equals("sprograma")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    semantico.insereTabela(token.getLexema(),"nomeDePrograma", "");
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
            tokenAnterior = token;
            token = lexico.buscaToken();
            if(token == null){
                if(tokenAnterior.getSimbolo().equals("sponto")){
                    acabouTokens = true;
                    return;
                }
                else{
                    token = tokenAnterior;
                    erro("Sintático", DescricaoErro.FALTA_PONTO_FINAL.getDescricao());
                }
                
            }
            else if(token.getErro()){
                erro("Lexico", DescricaoErro.LEXICO.getDescricao() + " '" + token.getLexema()+ "'");
            }
            
            if(token.getSimbolo().equals("sponto") && !tokenAnterior.getSimbolo().equals("sfim"))
                    semantico.erro("Semantico"
                        ,tokenAnterior.getLinha()
                        ,DescricaoErro.PONTO_FINAL_ERRADO.getDescricao());

        }catch(CompilerException err){
            throw err;
        }
    }
    
    private boolean analisaBloco() throws CompilerException{
        try{
            boolean atribuiFuncao;
            
            lexico();
            analisaEtVariaveis();
            analisaSubrotinas();
            
            atribuiFuncao = analisaComandos();
            
            return atribuiFuncao;
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
                    semantico.pesquisaDuplicVarTabela(token.getLexema(), token.getLinha());
                    semantico.insereTabela(token.getLexema(),"variavel", "");
                    lexico();
                    if(token.getSimbolo().equals("sdoispontos") || token.getSimbolo().equals("svirgula")){
                        if(token.getSimbolo().equals("svirgula")){
                            lexico();
                            if(token.getSimbolo().equals("sdoispontos")){
                                token = tokenAnterior;
                                erro("Sintático", DescricaoErro.VIRGULA_ERRADA.getDescricao());
                            }
                        }
                    }
                    else erro("Sintático", DescricaoErro.FALTA_VIRGULA_OU_DOIS_PONTO.getDescricao()
                            + "\nElemento incorreto: " + token.getLexema());
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
            semantico.colocaTipoTabela(token.getLexema(), token.getLinha());
            lexico(); 
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private boolean analisaComandos()throws CompilerException{
        try{
            boolean atribuiFuncao = false;
            if(token.getSimbolo().equals("sinicio")){
                lexico();
                atribuiFuncao = analisaComandoSimples();
                while(!token.getSimbolo().equals("sfim")){

                    if(token.getSimbolo().equals("sponto_virgula")){
                        lexico();
                        if(!token.getSimbolo().equals("sfim")) 
                            atribuiFuncao = analisaComandoSimples();
                    }
                    else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao() 
                            + "\n\nPossiveis outros erros:"
                            + "\nAtribuição incorreta;"
                            + "\nFalta '=';"
                            + "\n" + DescricaoErro.PONTO_FINAL_ERRADO.getDescricao() + ".");
                }   
                lexico();
            }
            else erro("Sintático", DescricaoErro.FALTA_INICIO.getDescricao());
            return atribuiFuncao;
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private boolean analisaComandoSimples()throws CompilerException{
        try{
            boolean atribuiFuncao = false;
            switch (token.getSimbolo()) {
                case "sidentificador":
                    atribuiFuncao = analisaAtribChprocedimento();
                    break;
                case "sse":
                    atribuiFuncao = analisaSe();
                    break;
                case "senquanto":
                    atribuiFuncao = analisaEnquanto();
                    break;
                case "sleia":
                    analisaLeia();
                    break;
                case "sescreva":
                    analisaEscreva();
                    break;
                default:
                    atribuiFuncao = analisaComandos();
                    break;
            }
            return atribuiFuncao;
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private boolean analisaAtribChprocedimento()throws CompilerException{
        try{
            boolean atribuiFuncao = false;
            lexico();

            if(token.getSimbolo().equals("satribuicao")) {
                atribuiFuncao = analisaAtribuicao(tokenAnterior);
            }
            else analisaChamadaDeProcedimento(tokenAnterior);
            
            return atribuiFuncao;
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
                    semantico.pesquisaDeclaraVarTabela(token.getLexema(), token.getLinha());
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
                    semantico.pesquisaDeclaraVarFuncaoTabela(token.getLexema(), token.getLinha());
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
    
    private boolean analisaEnquanto()throws CompilerException{
        try{
            boolean atribuiFuncao = false;
            boolean enquantoVerdadeiro = false;
            String auxrot1 = "L" + rotulo;
            String auxrot2;
            
            geracaoDeCodigo.geraNULL(auxrot1);
            rotulo += 1;
            
            lexico();
            if(!analisaExpressao().equals("booleano"))
                semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
            
            if(verdadeiroSosinho.equals("1 vez")){
                enquantoVerdadeiro = true;
            }
            
            if(token.getSimbolo().equals("sfaca")){
                auxrot2 = "L" + rotulo;
                geracaoDeCodigo.geraJMPF(auxrot2);
                rotulo += 1;
                
                lexico();
                atribuiFuncao = analisaComandoSimples();
                
                if(!enquantoVerdadeiro){
                    atribuiFuncao = false;
                }
                geracaoDeCodigo.geraJMP(auxrot1);
                geracaoDeCodigo.geraNULL(auxrot2);
            }
            else erro("Sintático", DescricaoErro.FALTA_FACA.getDescricao());
            return atribuiFuncao;
        }catch(CompilerException err){
            throw err;
        }
    }

    private boolean analisaSe()throws CompilerException{
        try{
            boolean seVerdadeiro = false;
            boolean atribuiFuncao = false;
            lexico();
            if(!analisaExpressao().equals("booleano"))
                semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
            
            if(verdadeiroSosinho.equals("1 vez")){
                    seVerdadeiro = true;
            }
            if(token.getSimbolo().equals("sentao")){
                lexico();
                atribuiFuncao = analisaComandoSimples();
                if(!seVerdadeiro){
                    atribuiFuncao = false;
                }
                if(token.getSimbolo().equals("ssenao")){
                    lexico();
                    atribuiFuncao = analisaComandoSimples();
                }
            }
            else erro("Sintático", DescricaoErro.FALTA_ENTAO.getDescricao());
            return atribuiFuncao;
        }catch(CompilerException err){
            throw err;
        }
    }

    private void analisaSubrotinas()throws CompilerException{
        try{
            String auxrot = "";
            boolean flag = false;
            
            if(token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")){
                auxrot = "L" + rotulo;
                geracaoDeCodigo.geraJMP(auxrot);
                rotulo += 1;
                flag = true;
            }
            
            while(token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")){
                
                if(token.getSimbolo().equals("sprocedimento")) analisaDeclaraçãoProcedimento(); 
                else analisaDeclaraçãoFunção();

                if(token.getSimbolo().equals("sponto_virgula")) lexico();
                else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
            }
            if(flag){
                geracaoDeCodigo.geraNULL(auxrot);
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaDeclaraçãoProcedimento()throws CompilerException{
        try{
            lexico();
            if(token.getSimbolo().equals("sidentificador")){
                semantico.pesquisaDuplicProcedimentoTabela(token.getLexema(), token.getLinha());
                semantico.insereTabela(token.getLexema(),"procedimento", "");
                lexico();
                if(token.getSimbolo().equals("sponto_virgula")){
                    analisaBloco();
                }
                else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
            }
            else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
            semantico.desempilhaTabela(token.getLinha());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaDeclaraçãoFunção()throws CompilerException{
        boolean atribuiFuncao;
        String nomeFuncao;
        try{
            lexico();
            if(token.getSimbolo().equals("sidentificador")){
                semantico.pesquisaDuplicFuncaoTabela(token.getLexema(), token.getLinha());
                semantico.insereTabela(token.getLexema(),"funcao", "");
                nomeFuncao = token.getLexema();
                lexico();
                if(token.getSimbolo().equals("sdoispontos")){
                    lexico();
                    if(token.getSimbolo().equals("sinteiro") || token.getSimbolo().equals("sbooleano")){
                        semantico.colocaTipoFuncao(token.getLexema());
                        lexico();
                        if(token.getSimbolo().equals("sponto_virgula")){
                            atribuiFuncao = analisaBloco();
                            if(!atribuiFuncao)
                                semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.FUNCAO_SEM_ATRIBUICAO.getDescricao() 
                                        + "\n\nFunção com erro: " + nomeFuncao);
                            
                            semantico.desempilhaTabela(token.getLinha());
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
    
    private String analisaExpressao()throws CompilerException{
        try{
            tipoVariavelExpressao = "null";
            verdadeiroSosinho = "null";
            String tipoExpressao = "inteiro";
            analisaExpressãoSimples();
            switch(token.getSimbolo()){
                case "smaior":
                case "smenor":
                case "smaiorig":
                case "sig":
                case "smenorig":
                case "sdif":
                    tipoExpressao = "booleano";
                    lexico();
                    analisaExpressãoSimples();
                    break;
            }    
            if(tipoExpressao.equals("booleano") || tipoVariavelExpressao.equals("booleano"))
                tipoExpressao = "booleano";
            
            return tipoExpressao;
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaExpressãoSimples()throws CompilerException{
        try{
            if(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")) 
                lexico();

            analisaTermo();

            while(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos") || token.getSimbolo().equals("sou")){
                if(token.getSimbolo().equals("sou")){
                    if(tipoVariavelExpressao.equals("inteiro")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                    }
                    tipoVariavelExpressao = "null";
                }
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
                if(token.getSimbolo().equals("se")) {
                    if(tipoVariavelExpressao.equals("inteiro")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                    }
                    tipoVariavelExpressao = "null";
                }
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
                verdadeiroSosinho = "falso";
                if(semantico.verificaTipoFuncaoTabela(token.getLexema(), token.getLinha()))
                    analisaChamadaDeFuncao();
                else {
                    semantico.pesquisaDeclaraVarTabela(token.getLexema(), token.getLinha());
                    
                    if(tipoVariavelExpressao.equals("null")){
                        tipoVariavelExpressao = semantico.getTipo(token.getLexema());                        
                    }
                    else{
                        semantico.tipoVar(token.getLexema(), token.getLinha(), tipoVariavelExpressao);
                    }
                    lexico();                                    
                }
            }
            else if(token.getSimbolo().equals("snumero")){
                verdadeiroSosinho = "falso";
                if(tipoVariavelExpressao.equals("null")){
                        tipoVariavelExpressao = "inteiro";                        
                 }
                else if(tipoVariavelExpressao.equals("booleano")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                }
                lexico();
            }
            else if(token.getSimbolo().equals("snao")){
                verdadeiroSosinho = "falso";
                tipoVariavelExpressao = "booleano";
                lexico();
                analisaFator();
            }    
            else if(token.getSimbolo().equals("sabre_parenteses")){
                if(tokenAnterior.getSimbolo().equals("snao")){
                    lexico();
                    if(analisaExpressao().equals("inteiro")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                    }
                    tipoVariavelExpressao = "booleano";
                }
                else{
                    lexico();
                    tipoVariavelExpressao = analisaExpressao();
                }
                
                if(token.getSimbolo().equals("sfecha_parenteses")){
                    lexico();
                }
                else erro("Sintático", DescricaoErro.FALTA_FECHA_PARENTESES.getDescricao());
            }

            else if(token.getLexema().equals("verdadeiro") || token.getLexema().equals("falso")){
                if(verdadeiroSosinho.equals("null") && token.getLexema().equals("verdadeiro")){
                    verdadeiroSosinho = "1 vez";
                }
                else
                    verdadeiroSosinho = "falso";
                
                if(tipoVariavelExpressao.equals("null")){
                        tipoVariavelExpressao = "booleano";                        
                 }
                else if(tipoVariavelExpressao.equals("inteiro")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                } 
                lexico();
            }
            else erro("Sintático", DescricaoErro.EXPRESSAO_INCORRETA.getDescricao() + token.getLexema());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaChamadaDeProcedimento(Token tokenAnterior)throws CompilerException{
        try{
            semantico.pesquisaDeclaraProcedimentoTabela(tokenAnterior.getLexema(), tokenAnterior.getLinha());
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaChamadaDeFuncao()throws CompilerException{
        try{
            semantico.pesquisaDeclaraFuncaoTabela(token.getLexema(), token.getLinha());
            lexico();
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private boolean analisaAtribuicao(Token variavel)throws CompilerException{
        try{
            boolean atribuiFuncao = false;
            String tipoExpressao;
            lexico();
            tipoExpressao = analisaExpressao();
            
           switch(semantico.tipoVar(variavel.getLexema(), variavel.getLinha(), tipoExpressao)){
               case "atribuiFuncao":
                   atribuiFuncao = true;
                   break;
           }
           
           geracaoDeCodigo.geraSTR(variavel.getLexema()); // falta informação
           
           return atribuiFuncao;
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void erro(String etapaErro, String descricao) throws CompilerException{
        semantico.restVariaveis();
        throw new CompilerException(token.getLinha(), etapaErro, descricao);
    }
    
    private void sucesso(){
        semantico.restVariaveis();
    }
}
