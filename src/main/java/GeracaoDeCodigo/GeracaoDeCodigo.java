package GeracaoDeCodigo;

import java.util.ArrayList;
import java.util.List;

public class GeracaoDeCodigo {
    private final List<Instrucao> codigo;
    
    public GeracaoDeCodigo(){
        this.codigo = new ArrayList<>();
    }
    
    public void setInstrucao(String rotulo, String nomeInstrucao, String parametro1, String parametro2){
        codigo.add(new Instrucao(rotulo, nomeInstrucao, parametro1, parametro2));
    }
    
    public void geraExpressao(String posfixa){
        String[] elementos = posfixa.split(" ");
        
        for (String elemento : elementos) {
            switch(elemento){
                case "+":
                    geraADD();
                    break;
                case "-":
                    geraSUB();
                    break;
                case "*":
                    geraMULT();
                    break;
                case "div":
                    geraDIVI();
                    break;
                case "-u":
                    geraINV();
                    break;
                case "e":
                    geraADD();
                    break;
                case "ou":
                    geraOR();
                    break;
                case "nao":
                    geraNEG();
                    break;
                case "<":
                    geraCME();
                    break;
                case ">":
                    geraCMA();
                    break;
                case "=":
                    geraCEQ();
                    break;
                case "!=":
                    geraCDIF();
                    break;
                case "<=":
                    geraCMEQ();
                    break;
                case ">=":
                    geraCMAQ();
                    break;
                case "verdadeiro":
                    geraLDC("1");
                    break;
                case "falso":
                    geraLDC("0");
                    break;
                default:
                    try{
                        Integer.parseInt(elemento);
                        geraLDC(elemento);
                    }catch(NumberFormatException err){
                        geraLDV(elemento); // falta info
                    }
                    break;

            }
        }
    }
    
    public void geraLDC(String parametro1){
        setInstrucao("", "LDC", parametro1, "");
    }
    public void geraLDV(String parametro1){
        setInstrucao("", "LDV", parametro1, "");
    }
    public void geraADD(){
        setInstrucao("", "ADD", "", "");
    }
    public void geraSUB(){
        setInstrucao("", "SUB", "", "");
    }
    public void geraMULT(){
        setInstrucao("", "MULT", "", "");
    }
    public void geraDIVI(){
        setInstrucao("", "DIVI", "", "");
    }
    public void geraINV(){
        setInstrucao("", "INV", "", "");
    }
    public void geraAND(){
        setInstrucao("", "AND", "", "");
    }
    public void geraOR(){
        setInstrucao("", "OR", "", "");
    }
    public void geraNEG(){
        setInstrucao("", "NEG", "", "");
    }
    public void geraCME(){
        setInstrucao("", "CME", "", "");
    }
    public void geraCMA(){
        setInstrucao("", "CMA", "", "");
    }
    public void geraCEQ(){
        setInstrucao("", "CEQ", "", "");
    }
    public void geraCDIF(){
        setInstrucao("", "CDIF", "", "");
    }
    public void geraCMEQ(){
        setInstrucao("", "CMEQ", "", "");
    }
    public void geraCMAQ(){
        setInstrucao("", "CMAQ", "", "");
    }
    public void geraSTART(String parametro1){
        setInstrucao("", "START", parametro1, "");
    }
    public void geraHLT(){
        setInstrucao("", "HLT", "", "");
    }
    public void geraSTR(String parametro1){
        setInstrucao("", "STR", parametro1, "");
    }
    public void geraJMP(String parametro1){
        setInstrucao("", "JMP", parametro1, "");
    }
    public void geraJMPF(String parametro1){
        setInstrucao("", "JMPF", parametro1, "");
    }
    public void geraNULL(String rotulo){
        setInstrucao(rotulo, "NULL", "", "");
    }
    public void geraRD(){
        setInstrucao("", "RD", "", "");
    }
    public void geraPRN(){
        setInstrucao("", "PRN", "", "");
    }
    public void geraALLOC(String parametro1, String parametro2){
        setInstrucao("", "ALLOC", parametro1, parametro2);
    }
    public void geraDALLOC(String parametro1, String parametro2){
        setInstrucao("", "DALLOC", parametro1, parametro2);
    }
    public void geraCALL(String parametro1){
        setInstrucao("", "CALL", parametro1, "");
    }
    public void geraRETURN(){
        setInstrucao("", "RETURN", "", "");
    }
}
