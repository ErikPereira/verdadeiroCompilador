/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeracaoDeCodigo;

public class Instrucao {
    private String rotulo;
    private String nomeInstrucao;
    private String parametro1;
    private String parametro2;
    
    public Instrucao(String rotulo, String nomeInstrucao, String parametro1, String parametro2){
        this.rotulo = rotulo;
        this.parametro1 = parametro1;
        this.parametro2 = parametro2;
        this.nomeInstrucao = nomeInstrucao;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public void setNomeInstrucao(String nomeInstrucao) {
        this.nomeInstrucao = nomeInstrucao;
    }

    public void setParametro1(String parametro1) {
        this.parametro1 = parametro1;
    }

    public void setParametro2(String parametro2) {
        this.parametro2 = parametro2;
    }

    public String getRotulo() {
        return rotulo;
    }

    public String getNomeInstrucao() {
        return nomeInstrucao;
    }

    public String getParametro1() {
        return parametro1;
    }

    public String getParametro2() {
        return parametro2;
    }
}
