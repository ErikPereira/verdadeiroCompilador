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
    
    public void insereTabela(String lexema, String tipoIdentificado, String rotulo){
        tabelaDeSimbolo.add(new Simbolo(tipoIdentificado, lexema, rotulo, marcaNivel));
        
        switch(tipoIdentificado){
            case "funcao":
            case "procedimento":
                marcaNivel += 1;
                break;
        }
    }
    
    public void pesquisaDuplicVarTabela(String variavel, int linha) throws CompilerException{
        try{
            int posicao = pesquisaTabela(variavel);
            if(posicao != -1){
                Simbolo simbolo = tabelaDeSimbolo.get(posicao);
                if(simbolo.getNivel() == 0  || simbolo.getTipoLexema().equals("funcao") ||
                        simbolo.getTipoLexema().equals("procedimento") || simbolo.getNivel() == marcaNivel){
                        erro("Semantico", linha, DescricaoErro.VARIAVEL_DUPLICADA.getDescricao() + ": " + variavel);
                }
            }
        }catch(CompilerException err){
            throw err;
        }
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
          try{
           int posicao = pesquisaTabela(variavel);
           if(posicao != -1){
                Simbolo simbolo = tabelaDeSimbolo.get(posicao);
                if(!simbolo.getTipoLexema().equals("variavel")){
                    erro("Semantico", linha, DescricaoErro.NAO_DECLARADA.getDescricao() + ": " + variavel);
                }
           }
        }catch(CompilerException err){
            throw err;
        } 
    }
    
    public void pesquisaDeclaraVarFuncaoTabela(String identificador, int linha) throws CompilerException{
        try{
            int posicao = pesquisaTabela(identificador);
            if(posicao != -1){
               Simbolo simbolo = tabelaDeSimbolo.get(posicao);
               if(!simbolo.getTipoLexema().equals("variavel") || !simbolo.getTipoLexema().equals("funcao")){
                   erro("Semantico", linha, DescricaoErro.NAO_DECLARADA.getDescricao() + ": " + identificador);
               }
           }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraFuncaoTabela(String nomeFuncao, int linha) throws CompilerException{
         try{
            int posicao = pesquisaTabela(nomeFuncao);
            if(posicao != -1){
               Simbolo simbolo = tabelaDeSimbolo.get(posicao);
               if(simbolo.getTipoLexema().equals("funcao")){
                   erro("Semantico", linha, DescricaoErro.FUNCAO_DUPLICADA.getDescricao()+ ": " + nomeFuncao);
               }

           }
        }catch(CompilerException err){
            throw err;
        }
    }
    
    public void pesquisaDeclaraProcedimentoTabela(String nomeProcedimento, int linha) throws CompilerException{
        try{
            int encontrou = pesquisaTabela(nomeProcedimento);
  
            if (encontrou != -1) // verificar 
                erro("Semantico", linha, DescricaoErro.PROCEDIMENTO_DUPLICADO.getDescricao() + ": " + nomeProcedimento);
            
        }catch(CompilerException err){
            throw err;
        }  
    }
    
    public void desempilhaTabela(int linha) throws CompilerException{
        int posicao = tabelaDeSimbolo.size() - 1;
        Simbolo simbolo = tabelaDeSimbolo.get(posicao);

        while(simbolo.getNivel() == marcaNivel){
            tabelaDeSimbolo.remove(posicao--);
            simbolo = tabelaDeSimbolo.get(posicao);   
        }
        marcaNivel --;
    }

    public void restVariaveis(){
        tabelaDeSimbolo.clear();
        marcaNivel = 0;
    }
    
    public void colocaTipoFuncao(String tipo){
        int ultimoInserido = tabelaDeSimbolo.size() - 1;
        Simbolo funcao =  tabelaDeSimbolo.get(ultimoInserido);
        funcao.setTipo("funcao " + tipo); // verifica aki
        tabelaDeSimbolo.set(ultimoInserido, funcao);
    }
    
    private int pesquisaTabela(String lexema) throws CompilerException{
        int posicao = -1;

        for(int i = tabelaDeSimbolo.size() - 1; i >= 0; i--){
            Simbolo simbolo = tabelaDeSimbolo.get(i);
            if(simbolo.getLexema().equals(lexema)){
                posicao = i;
            }
        }
        return posicao;
    }
     
    public boolean verificaTipoTabela(String lexema, int linha) throws CompilerException{ // especificar nome
        try{
            int posicao = pesquisaTabela(lexema);
            
            if(posicao == -1)
                erro("Semantico", linha, DescricaoErro.VARIAVEL_NAO_DECLARADA.getDescricao());
            
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
        restVariaveis();
        throw new CompilerException(linhaErro, etapaErro, descricao);
    }
}
