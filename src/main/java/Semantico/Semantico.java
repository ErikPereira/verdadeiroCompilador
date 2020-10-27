package Semantico;

import java.util.ArrayList;
import java.util.List;
import compilerException.CompilerException;

public class Semantico {
    public List<Simbolo> tabelaDeSimbolo;
    private int marcaNivel;
            
    public Semantico(){
        this.tabelaDeSimbolo = new ArrayList<>();
        this.marcaNivel = 0;
    }
    
    public void insereTabela(String tipoLexema, String lexema, String rotulo){
        tabelaDeSimbolo.add(new Simbolo(tipoLexema, lexema, rotulo, marcaNivel));
        
        switch(tipoLexema){
            case "funcao":
            case "procedimento":
                marcaNivel += 1;
                break;
        }
    }
    
    public void pesquisaDuplicVarTabela(int linha) throws CompilerException{
        try{
           erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void colocaTipoTabela(int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraVarTabela(String varival, int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraVarFuncaoTabela(int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraFuncaoTabela(String nomeFuncao, int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraProcedimentoTabela(String nomeProcedimento, int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void desempilhaTabela(int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }

    public void restVariaveis(int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void insereTipoFuncao(String tipo, int linha) throws CompilerException{
        try{
            erro("Semantico", linha);
        }catch(CompilerException err){
            throw err;
        }
    }
    
    private void erro(String etapaErro, int linhaErro) throws CompilerException{
        throw new CompilerException(linhaErro, etapaErro, "Primeiro teste");
    }
}
