package Sintatico;

import Lexico.Lexico;
import Lexico.Token;
import Semantico.Semantico;
import compilerException.CompilerException;
import Enum.DescricaoErro;
import GeracaoDeCodigo.GeracaoDeCodigo;
import Posfixa.Posfixa;

public class Sintatico {
    private final Lexico lexico;
    private final Semantico semantico;
    private final GeracaoDeCodigo geracaoDeCodigo;
    private final Posfixa posfixa;
    private Token token;
    private Token tokenAnterior;
    private boolean acabouTokens;
    private String tipoVariavelExpressao;
    private String verdadeiroSozinho;
    private int rotulo;
    private int posicaoDeMemoria;
    private int inicioAlloc;
    
    
    public Sintatico(String programa){
        this.lexico = new Lexico(programa);
        this.lexico.analisadorLexical();
        this.semantico = new Semantico();
        this.geracaoDeCodigo = new GeracaoDeCodigo(this.semantico);
        this.token = new Token("", "", 0, false);
        this.rotulo = 1;
        this.acabouTokens = false;
        this.tipoVariavelExpressao = "null";
        this.verdadeiroSozinho = "null";
        this.posfixa = new Posfixa();
        this.posicaoDeMemoria = 0;
        this.inicioAlloc = 0;
    }
    
    public String analisadorSintatico() throws CompilerException{
        try {
            int qtdDesempilha;
            
            geracaoDeCodigo.geraSTART();
            geracaoDeCodigo.geraALLOC(Integer.toString(inicioAlloc), "1");
            inicioAlloc += 1;
            posicaoDeMemoria += 1;
            lexico();
            if(token.getSimbolo().equals("sprograma")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    semantico.insereTabela(token.getLexema(),"nomeDePrograma", "", "");
                    lexico();
                    if(token.getSimbolo().equals("sponto_virgula")){
                        analisaBloco(); 
                        
                        if(token.getSimbolo().equals("sponto")){
                            lexico();
                            if(acabouTokens==true){
                                qtdDesempilha = semantico.desempilhaTabela(tokenAnterior.getLinha()) + 1;
                                inicioAlloc -= qtdDesempilha;
                                geracaoDeCodigo.geraDALLOC(Integer.toString(inicioAlloc), Integer.toString(qtdDesempilha));                               
                                sucesso();
                                return geracaoDeCodigo.conteudoArquivo();
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
            
            return "";
        }catch(CompilerException err){
            throw err;
        }
        finally{
            lexico.resetVariaveis();
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
                    semantico.insereTabela(token.getLexema(),"variavel", "", Integer.toString(posicaoDeMemoria));
                    
                    posicaoDeMemoria += 1;
                    
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
            int qtdAlloc;
            if(!token.getSimbolo().equals("sinteiro") && !token.getSimbolo().equals("sbooleano")) {
                erro("Sintático", DescricaoErro.FALTA_TIPO.getDescricao());
            }
            qtdAlloc = semantico.colocaTipoTabela(token.getLexema(), token.getLinha());
            geracaoDeCodigo.geraALLOC(Integer.toString(inicioAlloc), Integer.toString(qtdAlloc));
            inicioAlloc += qtdAlloc;
            
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
                    geracaoDeCodigo.geraRD(token.getLexema(), token.getLinha());
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
                    String tipoIdentificador = semantico.pesquisaDeclaraVarFuncaoTabela( token.getLexema(), token.getLinha());
                    
                    if(tipoIdentificador.equals("funcao")){
                        analisaChamadaDeFuncao();
                    }
                    else{
                        lexico();
                    }
                    geracaoDeCodigo.geraPRN(tokenAnterior.getLexema(), tokenAnterior.getLinha());
                    
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
            int linha = token.getLinha();
            if(!analisaExpressao().equals("booleano"))
                semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
            
            posfixa.fimExpressao();
            geracaoDeCodigo.geraExpressao(posfixa.lista, linha);
            posfixa.resetVariaveis();
            
            if(verdadeiroSozinho.equals("1 vez")){
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
            String auxrot1 = "";
            String auxrot2 = "";
            
            lexico();
            int linha = token.getLinha();
            if(!analisaExpressao().equals("booleano"))
                semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
            
            posfixa.fimExpressao();
            geracaoDeCodigo.geraExpressao(posfixa.lista, linha);
            posfixa.resetVariaveis();
            
            if(verdadeiroSozinho.equals("1 vez")){
                    seVerdadeiro = true;
            }
            if(token.getSimbolo().equals("sentao")){
                lexico();
                
                auxrot1 = "L" + rotulo;
                geracaoDeCodigo.geraJMPF(auxrot1);
                rotulo += 1;
                
                atribuiFuncao = analisaComandoSimples();
                if(!seVerdadeiro){
                    atribuiFuncao = false;
                }
                if(token.getSimbolo().equals("ssenao")){
                    lexico();
                    
                    auxrot2 = "L" + rotulo;
                    geracaoDeCodigo.geraJMP(auxrot2);
                    rotulo += 1;
                    geracaoDeCodigo.geraNULL(auxrot1);
                    
                    atribuiFuncao = analisaComandoSimples();
                    
                    geracaoDeCodigo.geraNULL(auxrot2);
                }
                else{
                    geracaoDeCodigo.geraNULL(auxrot1);
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
                
                if(token.getSimbolo().equals("sprocedimento")) 
                    analisaDeclaraçãoProcedimento(); 
                else 
                    analisaDeclaraçãoFunção();

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
            int qtdDesempilha;
            lexico();
            
            if(token.getSimbolo().equals("sidentificador")){
                semantico.pesquisaDuplicProcedimentoTabela(token.getLexema(), token.getLinha());
                semantico.insereTabela(token.getLexema(),"procedimento", "L" + rotulo, "");
                
                geracaoDeCodigo.geraNULL("L" + rotulo);
                rotulo += 1;
                
                lexico();
                if(token.getSimbolo().equals("sponto_virgula")){
                    analisaBloco();
                }
                else erro("Sintático", DescricaoErro.FALTA_PONTO_E_VIRGULA.getDescricao());
            }
            else erro("Sintático", DescricaoErro.FALTA_IDENTIFICADOR.getDescricao());
            
            
            qtdDesempilha = semantico.desempilhaTabela(token.getLinha());
            
            if(qtdDesempilha != 0){
                inicioAlloc -= qtdDesempilha;
                geracaoDeCodigo.geraDALLOC(Integer.toString(inicioAlloc), Integer.toString(qtdDesempilha));
            }
            geracaoDeCodigo.geraRETURN();
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaDeclaraçãoFunção()throws CompilerException{
        boolean atribuiFuncao;
        String nomeFuncao;
        int qtdDesempilha;
        
        try{
            lexico();
            if(token.getSimbolo().equals("sidentificador")){
                semantico.pesquisaDuplicFuncaoTabela(token.getLexema(), token.getLinha());
                semantico.insereTabela(token.getLexema(),"funcao", "L" + rotulo, "0");
                
                geracaoDeCodigo.geraNULL("L" + rotulo);
                rotulo += 1;
                
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
                            
                            qtdDesempilha = semantico.desempilhaTabela(token.getLinha());
                            
                            if(qtdDesempilha != 0){
                                inicioAlloc -= qtdDesempilha;
                                geracaoDeCodigo.geraDALLOC(Integer.toString(inicioAlloc), Integer.toString(qtdDesempilha));
                            }
                            geracaoDeCodigo.geraRETURN();
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
            boolean eRelacional = true;
            tipoVariavelExpressao = "null";
            verdadeiroSozinho = "null";
            String tipoExpressao = "inteiro";
            analisaExpressãoSimples(tipoExpressao);
            
            while(eRelacional){
                switch(token.getSimbolo()){
                    case "smaior":
                    case "smenor":
                    case "smaiorig":
                    case "sig":
                    case "smenorig":
                    case "sdif":
                        tipoExpressao = "booleano";
                        
                        if(token.getSimbolo().equals("smaior") || token.getSimbolo().equals("smaiorig") || 
                            token.getSimbolo().equals("smenor") || token.getSimbolo().equals("smenorig")){
                            posfixa.inserePilha(token.getLexema(), 4);
                        }
                        else posfixa.inserePilha(token.getLexema(), 3);
                        
                        lexico();
                        analisaExpressãoSimples(tipoExpressao);
                        break;
                    default:
                        eRelacional = false;
                        break;
                } 
            }
            
            if(tipoExpressao.equals("booleano") || tipoVariavelExpressao.equals("booleano"))
                tipoExpressao = "booleano";
            
            return tipoExpressao;
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaExpressãoSimples(String tipoExpressao)throws CompilerException{
        try{
            if(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")){ 
                posfixa.inserePilha(token.getLexema(), 7);
                lexico();
            }

            analisaTermo(tipoExpressao);

            while(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos") || token.getSimbolo().equals("sou")){
                if(token.getSimbolo().equals("sou")){
                    posfixa.inserePilha(token.getLexema(), 1);
                    if(tipoVariavelExpressao.equals("inteiro") && tipoExpressao.equals("inteiro")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                    }
                    tipoVariavelExpressao = "null";
                }
                else
                    posfixa.inserePilha(token.getLexema(), 5);
                
                lexico();
                analisaTermo(tipoExpressao);
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaTermo(String tipoExpressao)throws CompilerException{
        try{
            analisaFator();
            while(token.getSimbolo().equals("smult") || token.getSimbolo().equals("sdiv") || token.getSimbolo().equals("se")){
                if(token.getSimbolo().equals("se")) {
                    posfixa.inserePilha(token.getLexema(), 2);
                    if(tipoVariavelExpressao.equals("inteiro") && tipoExpressao.equals("inteiro")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                    }
                    tipoVariavelExpressao = "null";
                }
                else
                    posfixa.inserePilha(token.getLexema(), 6);
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
                verdadeiroSozinho = "falso";
                if(semantico.verificaTipoFuncaoTabela(token.getLexema(), token.getLinha())){
                    posfixa.insereLista(token.getLexema());
                    analisaChamadaDeFuncao();
                }
                else {
                    semantico.pesquisaDeclaraVarTabela(token.getLexema(), token.getLinha());
                    
                    if(tipoVariavelExpressao.equals("null")){
                        tipoVariavelExpressao = semantico.getTipo(token.getLexema(), token.getLinha());                        
                    }
                    else{
                        semantico.tipoVar(token.getLexema(), token.getLinha(), tipoVariavelExpressao);
                    }
                    posfixa.insereLista(token.getLexema());
                    lexico();                                    
                }
            }
            else if(token.getSimbolo().equals("snumero")){
                verdadeiroSozinho = "falso";
                if(tipoVariavelExpressao.equals("null")){
                        tipoVariavelExpressao = "inteiro";                        
                 }
                else if(tipoVariavelExpressao.equals("booleano")){
                        semantico.erro("Semantico"
                                        ,tokenAnterior.getLinha()
                                        ,DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                }
                posfixa.insereLista(token.getLexema());
                lexico();
            }
            else if(token.getSimbolo().equals("snao")){
                verdadeiroSozinho = "falso";
                tipoVariavelExpressao = "booleano";
                posfixa.inserePilha(token.getLexema(), 7);
                lexico();
                analisaFator();
            }    
            else if(token.getSimbolo().equals("sabre_parenteses")){
                posfixa.inserePilha("(", 10);
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
                    posfixa.fechaParenteses();
                    lexico();
                }
                else erro("Sintático", DescricaoErro.FALTA_FECHA_PARENTESES.getDescricao());
            }

            else if(token.getLexema().equals("verdadeiro") || token.getLexema().equals("falso")){
                posfixa.insereLista(token.getLexema());
                if(verdadeiroSozinho.equals("null") && token.getLexema().equals("verdadeiro")){
                    verdadeiroSozinho = "1 vez";
                }
                else
                    verdadeiroSozinho = "falso";
                
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
            String rotuloFuncao ;
            semantico.pesquisaDeclaraProcedimentoTabela(tokenAnterior.getLexema(), tokenAnterior.getLinha());
            
            rotuloFuncao = semantico.getRotuloFuncProced(tokenAnterior.getLexema(), token.getLinha());
            geracaoDeCodigo.geraCALL(rotuloFuncao);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void analisaChamadaDeFuncao()throws CompilerException{
        try{
            String rotuloFuncao;
            
            semantico.pesquisaDeclaraFuncaoTabela(token.getLexema(), token.getLinha());
            
            rotuloFuncao = semantico.getRotuloFuncProced(token.getLexema(), token.getLinha());
            geracaoDeCodigo.geraCALL(rotuloFuncao);
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
            int linha = token.getLinha();
            tipoExpressao = analisaExpressao(); // informa se a expressao eh inteiro ou booleano
            
            posfixa.fimExpressao();
            geracaoDeCodigo.geraExpressao(posfixa.lista, linha);
            posfixa.resetVariaveis();
            
           switch(semantico.tipoVar(variavel.getLexema(), variavel.getLinha(), tipoExpressao)){
               case "atribuiFuncao":
                   atribuiFuncao = true;
                   break;
           }
           
           geracaoDeCodigo.geraSTR(variavel.getLexema(), variavel.getLinha());

           return atribuiFuncao;
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void erro(String etapaErro, String descricao) throws CompilerException{
        semantico.resetVariaveis();
        throw new CompilerException(token.getLinha(), etapaErro, descricao);
    }
    
    private void sucesso(){
        geracaoDeCodigo.geraHLT();
        semantico.resetVariaveis();
    }
}
