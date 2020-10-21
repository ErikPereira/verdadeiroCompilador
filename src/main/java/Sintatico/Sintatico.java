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
                            else  erro("Sintático");
                        }
                        else erro("Sintático");
                    }
                    else erro("Sintático");
                }
                else erro("Sintático");
            }
            else erro("Sintático");
            
        }catch(Exception err){
            throw err;
        }finally{
            lexico.restVariaveis();
        }
    }
    
    private void lexico()throws Exception{
        try{
            Token finalizouErrado = token;
            token = lexico.buscaToken();
            if(token == null){
                if(finalizouErrado.getSimbolo().equals("sponto"))
                    acabouTokens = true;
                else{
                    token = finalizouErrado;
                    erro("Sintático");
                }
                
            }
            else if(token.getErro()){
                erro("Lexico");
            }
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaBloco() throws Exception{
        try{
            lexico();
            analisaEtVariaveis();
            analisaSubrotinas();
            analisaComandos();
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaEtVariaveis()throws Exception{
        try{
            if(token.getSimbolo().equals("svar")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    while(token.getSimbolo().equals("sidentificador")){
                        analisaVariaveis();
                        if(token.getSimbolo().equals("sponto_virgula")) lexico();
                        else erro("Sintático");
                    }
                }
                else erro("Sintático");
            }
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaVariaveis()throws Exception{
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
                                erro("Sintático");
                            }
                        }
                    }
                    else erro("Sintático");
                }
                else erro("Sintático");
            }while(!token.getSimbolo().equals("sdoispontos"));
            lexico();
            analisaTipo();
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaTipo()throws Exception{
        try{
            if(!token.getSimbolo().equals("sinteiro") && !token.getSimbolo().equals("sbooleano")) erro("Sintático");
            lexico(); 
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaComandos()throws Exception{
        try{
            if(token.getSimbolo().equals("sinicio")){
                lexico();
                analisaComandoSimples();
                while(!token.getSimbolo().equals("sfim")){

                    if(token.getSimbolo().equals("sponto_virgula")){
                        lexico();
                        if(!token.getSimbolo().equals("sfim")) analisaComandoSimples();
                    }
                    else erro("Sintático");
                }
                lexico();
            }
            else erro("Sintático");
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaComandoSimples()throws Exception{
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
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaAtribChprocedimento()throws Exception{
        try{
        lexico();
        
        if(token.getSimbolo().equals("satribuicao")) analisaAtribuicao();
        else analisaChamadaDeProcedimento();
        
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaLeia()throws Exception{
        try{
            lexico();
            if(token.getSimbolo().equals("sabre_parenteses")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    lexico();
                    if(token.getSimbolo().equals("sfecha_parenteses")){
                        lexico();
                    }
                    else erro("Sintático");
                }
                else erro("Sintático");
            }
            else erro("Sintático");
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaEscreva()throws Exception{
        try{
            lexico();
            if(token.getSimbolo().equals("sabre_parenteses")){
                lexico();
                if(token.getSimbolo().equals("sidentificador")){
                    lexico();
                    if(token.getSimbolo().equals("sfecha_parenteses")){
                        lexico();
                    }
                    else erro("Sintático");
                }
                else erro("Sintático");
            }
            else erro("Sintático");
    
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaEnquanto()throws Exception{
        try{
            lexico();
            analisaExpressao();
            if(token.getSimbolo().equals("sfaca")){
                lexico();
                analisaComandoSimples();
            }
        else erro("Sintático");
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaSe()throws Exception{
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
            else erro("Sintático");
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaSubrotinas()throws Exception{
        try{
            while(token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")){
                
                if(token.getSimbolo().equals("sprocedimento")) analisaDeclaraçãoProcedimento(); 
                else analisaDeclaraçãoFunção();

                if(token.getSimbolo().equals("sponto_virgula")) lexico();
                else erro("Sintático");
            }
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaDeclaraçãoProcedimento()throws Exception{
        try{
            lexico();
            if(token.getSimbolo().equals("sidentificador")){
                lexico();
                if(token.getSimbolo().equals("sponto_virgula")){
                    analisaBloco();
                }
                else erro("Sintático");
            }
            else erro("Sintático");
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaDeclaraçãoFunção()throws Exception{
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
                    else erro("Sintático");
                }
                else erro("Sintático");
            }
            else erro("Sintático");
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaExpressao()throws Exception{
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
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaExpressãoSimples()throws Exception{
        try{
            if(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")) lexico();            
            analisaTermo();
            while(token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos") || token.getSimbolo().equals("sou")){
                lexico();
                analisaTermo();            
            }     
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaTermo()throws Exception{
        try{
            analisaFator();
            while(token.getSimbolo().equals("smult") || token.getSimbolo().equals("sdiv") || token.getSimbolo().equals("se")){
                lexico();
                analisaFator();       
            }
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaFator()throws Exception{
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
                else erro("Sintático");
            }

            else if(token.getLexema().equals("verdadeiro") || token.getLexema().equals("falso")) lexico();
            else erro("Sintático");
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaChamadaDeProcedimento()throws Exception{
        try{
            
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaChamadaDeFuncao()throws Exception{
        try{
            lexico();
        }catch(Exception err){
            throw err;
        }
    }
    
    private void analisaAtribuicao()throws Exception{
        try{
            lexico();
            analisaExpressao();
        }catch(Exception err){
            throw err;
        }
    }
    
    private void erro(String etapaErro) throws Exception{
        Exception err = new Exception(Integer.toString(token.getLinha()));
        String mensagemErro = "Erro " + etapaErro + "\n\n"
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
