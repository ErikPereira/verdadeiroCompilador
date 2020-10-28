package Semantico;

import java.util.ArrayList;
import java.util.List;
import compilerException.CompilerException;
import Enum.DescricaoErro;

public class Semantico {
    public List<Simbolo> tabelaDeSimbolo;
    private int marcaNivel;
            
    public Semantico(){
        this.tabelaDeSimbolo = new ArrayList<>();
        this.marcaNivel = 0;
    }
    
    public void insereTabela(String lexema, String tipoLexema, String rotulo){
        tabelaDeSimbolo.add(new Simbolo(tipoLexema, lexema, rotulo, marcaNivel));
        
        switch(tipoLexema){
            case "funcao":
            case "procedimento":
                marcaNivel += 1;
                break;
        }
    }
    
    public void pesquisaDuplicVarTabela(String variavel, int linha) throws CompilerException{
        /*
        try{
           erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
        */
    }
    
    public void colocaTipoTabela(String tipo, int linha) throws CompilerException{
        /*
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
        */
    }
    
    public void pesquisaDeclaraVarTabela(String variavel, int linha) throws CompilerException{
        /*try{
           
        }catch(CompilerException err){
            throw err;
        } */ 
    }
    
    public void pesquisaDeclaraVarFuncaoTabela(String funcao, int linha) throws CompilerException{
        /*try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }*/
    }
    
    public void pesquisaDeclaraFuncaoTabela(String nomeFuncao, int linha) throws CompilerException{
        /*try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }*/
    }
    
    public void pesquisaDeclaraProcedimentoTabela(String nomeProcedimento, int linha) throws CompilerException{
        /*try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }*/
    }
    
    public void desempilhaTabela(int linha) throws CompilerException{
        /*try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }*/
    }

    public void restVariaveis(){
   
    }
    
    public void colocaTipoFuncao(String tipo){
        int ultimoInserido = tabelaDeSimbolo.size() - 1;
        Simbolo funcao =  tabelaDeSimbolo.get(ultimoInserido);
        funcao.setTipo(tipo);
        tabelaDeSimbolo.set(ultimoInserido, funcao);
    }
    
    private int pesquisaTabela(String lexema, int linha) throws CompilerException{
        try{
            boolean encontrou = false;
            int posicao = -1;
            
            for(int i = tabelaDeSimbolo.size() - 1; i >= 0; i--){
                Simbolo simbolo = tabelaDeSimbolo.get(i);
                if(simbolo.getLexema().equals(lexema)){
                    encontrou = true;
                    posicao = i;
                }
            }
            
            if(!encontrou) erro("Semantico", linha, DescricaoErro.VARIAVEL_NAO_DECLARADA.getDescricao());
            
            return posicao;
        }catch(CompilerException err){
            throw err;
        }  
    }
    
    public boolean verificaTipoTabela(String lexema, int linha) throws CompilerException{
        try{
            int posicao = pesquisaTabela(lexema, linha);
            Simbolo simbolo = tabelaDeSimbolo.get(posicao);
            
            switch(simbolo.getTipo()){
                case "funcaoInteiro":
                case "funcaoBooleano":
                    return true;
            }
            return false;
        }catch(CompilerException err){
            throw err;
        }  
    }
    
    
    private void erro(String etapaErro, int linhaErro, String descricao) throws CompilerException{
        throw new CompilerException(linhaErro, etapaErro, descricao);
    }
}
