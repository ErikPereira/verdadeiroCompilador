package compilerException;

public class CompilerException extends Exception{
    private int linhaErro;
    private final String etapa;
    private final String descricao;
    
    public CompilerException(int linhaErro, String etapa, String descricao){
        this.descricao = descricao;
        this.linhaErro = linhaErro;
        this.etapa = etapa;  
    }
    
    @Override
    public String getMessage(){
        String mensagemErro = "Erro " + etapa + "\n\n"
                            + "Linha: " + linhaErro + "\n\n"
                            + "Descrição do erro: " + descricao;
        
        return mensagemErro;
    }
    
    public int getLinhaErro(){
        return this.linhaErro;
    }
}
