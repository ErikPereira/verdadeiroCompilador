package Semantico;

import java.util.ArrayList;
import java.util.List;
import compilerException.CompilerException;
import Enum.DescricaoErro;


public class Semantico {
    private final List<Simbolo> tabelaDeSimbolo;
    private int marcaNivel;
            
    public Semantico(){
        this.tabelaDeSimbolo = new ArrayList<>();
        this.marcaNivel = 0;
    }
    
    public void insereTabela(String lexema, String tipoIdentificado, String rotulo, String posicaoMemoria){
        tabelaDeSimbolo.add(new Simbolo(tipoIdentificado, lexema, rotulo, marcaNivel, posicaoMemoria));
        
        switch(tipoIdentificado){
            case "funcao":
            case "procedimento":
                marcaNivel += 1;
                break;
        }
    }
    
    public void pesquisaDuplicVarTabela(String variavel, int linha) throws CompilerException{
        try{
            int posicao = pesquisaTabela(variavel,tabelaDeSimbolo.size() - 1);
            while(posicao!= -1){
                Simbolo simbolo = tabelaDeSimbolo.get(posicao);
                if(    simbolo.getNivel() == marcaNivel 
                    || simbolo.getTipoLexema().equals("procedimento") 
                    || simbolo.getTipoLexema().equals("funcao")
                ){
                    erro("Semantico", linha, DescricaoErro.VARIAVEL_DUPLICADA.getDescricao() + ": " + variavel);
                }
                posicao = pesquisaTabela(variavel,posicao-1);
            }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public int colocaTipoTabela(String tipo, int linha){ 
        int posicao = tabelaDeSimbolo.size() - 1;
        int quantidade = 0;
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);
        
        while( simbolo.getTipo().equals("") 
                && simbolo.getTipoLexema().equals("variavel")
                && simbolo.getNivel() == marcaNivel){
            
            simbolo.setTipo(tipo);
            tabelaDeSimbolo.set(posicao, simbolo);
            posicao--;
            simbolo = tabelaDeSimbolo.get(posicao);    
            quantidade += 1;
        }  
        return quantidade;
    }
    
    public void pesquisaDeclaraVarTabela(String variavel, int linha) throws CompilerException{
          try{
            int posicao = pesquisaTabela(variavel, tabelaDeSimbolo.size() - 1);
            if(posicao != -1){
                Simbolo simbolo = tabelaDeSimbolo.get(posicao);
                if(!simbolo.getTipoLexema().equals("variavel")){
                    erro("Semantico", linha, DescricaoErro.NAO_É_VARIAVEL.getDescricao() + ": " + variavel);
                }
            }
            else{
                erro("Semantico", linha, DescricaoErro.NAO_DECLARADA.getDescricao() + ": " + variavel);
            }
        }catch(CompilerException err){
            throw err;
        } 
    }
    
    public String pesquisaDeclaraVarFuncaoTabela(String identificador, int linha) throws CompilerException{
        try{
            int posicao = pesquisaTabela(identificador, tabelaDeSimbolo.size() - 1);
            if(posicao != -1){
                Simbolo simbolo = tabelaDeSimbolo.get(posicao);
                if(!simbolo.getTipoLexema().equals("variavel") && !simbolo.getTipoLexema().equals("funcao")){
                   erro("Semantico", linha, DescricaoErro.NAO_É_VARIAVEL_FUNCAO.getDescricao() + ": " + identificador);
                }
                return simbolo.getTipoLexema();
           }
            else{
                erro("Semantico", linha, DescricaoErro.NAO_DECLARADA.getDescricao() + ": " + identificador);
            }
            return "";
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDuplicFuncaoTabela(String nomeFuncao, int linha) throws CompilerException{
         try{
            int posicao = pesquisaTabela(nomeFuncao, tabelaDeSimbolo.size() - 1);
            if(posicao != -1)
                erro("Semantico", linha, DescricaoErro.FUNCAO_DUPLICADA.getDescricao()+ ": " + nomeFuncao);

        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraFuncaoTabela(String nomeFuncao, int linha) throws CompilerException{
         try{
            int posicao = pesquisaTabela(nomeFuncao, tabelaDeSimbolo.size() - 1);
            if(posicao == -1)
                erro("Semantico", linha, DescricaoErro.FUNCAO_NAO_DECLARADO.getDescricao()+ ": " + nomeFuncao);
            
            Simbolo funcao = tabelaDeSimbolo.get(posicao);
            
            if(!funcao.getTipoLexema().equals("funcao"))
                erro("Semantico", linha, DescricaoErro.NAO_É_PROCEDIMENTO.getDescricao() 
                        + "\n\nIdentificador: " + nomeFuncao
                        + "\nO que é: " + funcao.getTipoLexema());
            
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDuplicProcedimentoTabela(String nomeProcedimento, int linha) throws CompilerException{
        try{
            int posicao = pesquisaTabela(nomeProcedimento, tabelaDeSimbolo.size() - 1);
  
            if (posicao != -1)
                erro("Semantico", linha, DescricaoErro.PROCEDIMENTO_DUPLICADO.getDescricao() + ": " + nomeProcedimento);
            
        }catch(CompilerException err){
            throw err;
        }  
    }
    
    public void pesquisaDeclaraProcedimentoTabela(String nomeProcedimento, int linha) throws CompilerException{
        try{
            int posicao = pesquisaTabela(nomeProcedimento, tabelaDeSimbolo.size() - 1);
  
            if (posicao == -1)
                erro("Semantico", linha, DescricaoErro.PROCEDIMENTO_NAO_DECLARADO.getDescricao() + ": " + nomeProcedimento);
            
            Simbolo procedimento = tabelaDeSimbolo.get(posicao);
            
            if(!procedimento.getTipoLexema().equals("procedimento"))
                erro("Semantico", linha, DescricaoErro.NAO_É_PROCEDIMENTO.getDescricao() 
                        + "\n\nIdentificador: " + nomeProcedimento
                        + "\nO que é: " + procedimento.getTipoLexema()
                        + "\n\n" + DescricaoErro.POSSIVEIS_ERROS.getDescricao());
            
        }catch(CompilerException err){
            throw err;
        }  
    }
    
    public int  desempilhaTabela(int linha) throws CompilerException{
        int posicao = tabelaDeSimbolo.size() - 1;
        int qtdDesempilha = 0;
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);
        
        while(simbolo.getNivel() == marcaNivel && !simbolo.getTipoLexema().equals("nomeDePrograma")){
           if(simbolo.getTipoLexema().equals("variavel")){
                qtdDesempilha += 1;  
           }
           
            tabelaDeSimbolo.remove(posicao--);
            simbolo = tabelaDeSimbolo.get(posicao);            
        }
        marcaNivel --;
        return qtdDesempilha;
    }

    public void resetVariaveis(){
        tabelaDeSimbolo.clear();
        marcaNivel = 0;
    }
    
    public void colocaTipoFuncao(String tipo){
        int ultimoInserido = tabelaDeSimbolo.size() - 1;
        Simbolo funcao =  tabelaDeSimbolo.get(ultimoInserido);
        funcao.setTipo("funcao " + tipo);
        tabelaDeSimbolo.set(ultimoInserido, funcao);
    }
    
    private int pesquisaTabela(String lexema, int inicio) throws CompilerException{
        int posicao = -1;

        for(int i = inicio; i >= 0; i--){
            Simbolo simbolo = tabelaDeSimbolo.get(i);
            if(simbolo.getLexema().equals(lexema)){
                return i;
            }
        }
        return posicao;
    }
    
    public boolean verificaTipoFuncaoTabela(String lexema, int linha) throws CompilerException{ // especificar nome
        try{
            int posicao = pesquisaTabela(lexema, tabelaDeSimbolo.size() - 1);
            
            if(posicao == -1)
                erro("Semantico", linha, DescricaoErro.VARIAVEL_NAO_DECLARADA.getDescricao() + ": " + lexema);
            
            Simbolo simbolo = tabelaDeSimbolo.get(posicao);

            switch(simbolo.getTipo()){
                case "funcao inteiro":
                case "funcao booleano":
                    return true;
            }
            return false;
        }catch(CompilerException err){
            throw err;
        }  
    }
    
    public String tipoVar(String lexema, int linha, String tipo) throws CompilerException{

        int posicao = pesquisaTabela(lexema, tabelaDeSimbolo.size() - 1);
        
        if (posicao == -1)
            erro("Semantico", linha, DescricaoErro.NAO_DECLARADA.getDescricao() + ": " + lexema);
            
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);

        if(!simbolo.getTipoLexema().equals("variavel") && !simbolo.getTipoLexema().equals("funcao")){
            erro("Semantico", linha, DescricaoErro.NAO_É_VARIAVEL_FUNCAO.getDescricao());
        }

        else if(simbolo.getTipoLexema().equals("funcao")){
            if(simbolo.getNivel() != marcaNivel - 1 ){
                erro("Semantico", linha, DescricaoErro.ATRIBUI_FUNCAO_FORA_DO_ESCOPO.getDescricao());
            }
            switch(simbolo.getTipo()){
                case "funcao inteiro":
                    if(!tipo.equals("inteiro")){
                        erro("Semantico", linha, DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                    }   
                    break;
                case "funcao booleano":
                    if(!tipo.equals("booleano")){
                        erro("Semantico", linha, DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
                    }
                    break;
            }
            return "atribuiFuncao";
        }
        else if(!simbolo.getTipo().equals(tipo)){
            erro("Semantico", linha, DescricaoErro.TIPOS_INCOMPATÍVEIS.getDescricao());
        }
        return "";
    }
    
    public String getTipo(String lexema, int linha) throws CompilerException{
        int posicao = pesquisaTabela(lexema, tabelaDeSimbolo.size() - 1);
        
        if(posicao == -1)
            erro("Semantico", linha, DescricaoErro.VARIAVEL_NAO_DECLARADA.getDescricao()+ ": " + lexema);
            
        
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);
        String tipo = simbolo.getTipo();
        
        switch(tipo){
            case "funcao inteiro":
                tipo = "inteiro";
                break;
            case "funcao booleano":
                tipo = "booleano";
                break;
        }
        
        return tipo;
    }
    
    public String getPosicaoMemoria(String lexema, int linha) throws CompilerException{
        int posicao = pesquisaTabela(lexema, tabelaDeSimbolo.size() - 1);
        
        if(posicao == -1)
            erro("Semantico", linha, DescricaoErro.VARIAVEL_NAO_DECLARADA.getDescricao()+ "ola: " + lexema);
            
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);
        return simbolo.getPosicaoMemoria();
    }
    
    public String getRotuloFuncProced(String lexema, int linha)throws CompilerException{
        int posicao = pesquisaTabela(lexema, tabelaDeSimbolo.size() - 1);
        
        if(posicao == -1)
            erro("Semantico", linha, DescricaoErro.VARIAVEL_NAO_DECLARADA.getDescricao()+ ": " + lexema);
            
        
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);
        return simbolo.getRotulo();
    }
    
    public String getTipoLexema(String lexema, int linha)throws CompilerException{
        int posicao = pesquisaTabela(lexema, tabelaDeSimbolo.size() - 1);
        
        if(posicao == -1)
            erro("Semantico", linha, DescricaoErro.NAO_DECLARADA.getDescricao()+ ": " + lexema);
            
        
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);
        return simbolo.getTipoLexema();
    }
    
    public void erro(String etapaErro, int linhaErro, String descricao) throws CompilerException{
        resetVariaveis();
        throw new CompilerException(linhaErro, etapaErro, descricao);
    }
}
