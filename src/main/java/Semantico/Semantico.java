package Semantico;

import java.util.ArrayList;
import java.util.List;

public class Semantico {
    public List<Simbolo> tabelaDeSimbolo;
    
    public Semantico(){
        this.tabelaDeSimbolo = new ArrayList<>();
    }
    
    public void insereTabela(String tipoLexema, String lexema, String rotulo, int nivel) throws Exception{
        try{
            tabelaDeSimbolo.add(new Simbolo(tipoLexema, lexema, rotulo, nivel));
        }catch(Exception err){
            throw err;
        }
    }
    
    public void pesquisaDuplicVarTabela() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    public void colocaTipoTabela() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraVarTabela() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraVarFuncaoTabela() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraFuncaoTabela() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraProcedimentoTabela() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    public void desempilhaTabela() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    private void erro() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    public void restVariaveis() throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
}
