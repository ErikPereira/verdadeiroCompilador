
package Sintatico;
import Lexico.Lexico;
import Lexico.Token;

public class Sintatico {
    private Lexico lexico;
    private Token token;
    private javax.swing.JTextArea textResultado;
    private boolean acabouTokens = false;
    
    public Sintatico(String programa, javax.swing.JTextArea textResultado){
        this.textResultado = textResultado;
        this.lexico = new Lexico(programa);
        this.lexico.analisadorLexical();
    }
    
    public void analisadorSintatico() throws Exception{
        try{
            //sucesso();
           while(!acabouTokens) lexico();
            
        }catch(Exception err){
            throw err;
        }finally{
            lexico.restVariaveis();
        }
    }
    
    private void lexico()throws Exception{
        try{
            token = lexico.buscaToken();
            if(token == null){
                acabouTokens = true;
                sucesso();
            }
            else if(token.getErro()){
                erro("Lexico");
            }
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaBloco(){
    
    }
    
    private void analisaEtVariaveis(){
    
    }
    
    private void analisaVariaveis(){
    
    }
    
    private void analisaTipo(){
    
    }
    
    private void analisaComandos(){
    
    }
    
    private void analisaComandoSimples(){
    
    }
    
    private void analisaAtribChprocedimento(){
    
    }
    
    private void analisaLeia(){
    
    }
    
    private void analisaEscreva(){
    
    }
    
    private void analisaEnquanto(){
    
    }
    
    private void analisaSe(){
    
    }
    
    private void analisaSubrotinas(){
    
    }
    
    private void analisaDeclaraçãoProcedimento(){
    
    }
    
    private void analisaDeclaraçãoFunção(){
    
    }
    
    private void analisaExpressao(){
    
    }
    
    private void analisaExpressãoSimples(){
    
    }
    
    private void analisaTermo(){
    
    }
    
    private void analisaFator(){
    
    }
    
    private void analisaChamadaDeProcedimento(){
    
    }
    
    private void analisaChamadaDeFuncao(){
    
    }
    
    private void analisaAtribuicao(){
    
    }
    
    private void erro(String etapaErro) throws Exception{
        Exception err = new Exception(Integer.toString(token.getLinha()));
        String mensagemErro = "Erro na etapa: " + etapaErro + "\n\n"
                            + "Linha: " + err.getMessage() + "\n"
                            + "Simbolo: " + token.getSimbolo() + "\n"
                            + "Lexema: " + token.getLexema()+ "\n";
        textResultado.setText(mensagemErro);
        throw err;
    }
    
    private void sucesso(){
        textResultado.setText("\n\n   Execução realizada com Sucesso!");
    }
}
