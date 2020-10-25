/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;


public enum DescricaoErro {
    LEXICO("Elemento não identificado: "),
    NAO_ACABOU("Código encontrado após 'ponto final'(.)"),
    DUAS_VIRGULAS("Duas virgulas encontradas, 'dois pontos'(:) era esperado"),
    FALTA_PONTO_FINAL("'ponto final'(.) não identificado"),
    FALTA_PONTO_E_VIRGULA("Era esperado um 'Ponto e virgula'(;)"),
    FALTA_IDENTIFICADOR("Era esperado um Indentificador"),
    FALTA_NOME_PROGRAMA("Era esperado o nome do programa"),
    VIRGULA_OU_DOIS_PONTO("ERA esperado 'virgula'(,) ou 'dois pontos'(:)");
    
    private String descricao;
    
    DescricaoErro(String descricao){
        this.descricao = descricao;
    }
    
    public String getDescricao(){
        return this.descricao;
    }
}
