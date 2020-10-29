/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;


public enum DescricaoErro {
    // Lexico
    LEXICO("Elemento não identificado"),
    
    // Sintatico
    NAO_ACABOU("Código encontrado após 'ponto final'(.)"),
    VIRGULA_ERRADA("'Ponto e virgula'(;) encontrada no lugar errado"),
    PONTO_VIRGULA_ANTES_SENAO("Não é permitido 'ponto e virgula'(;) antes de um 'senao'"),
    FALTA_TIPO("Era esperado um tipo (inteiro ou booleano)"),
    FALTA_INICIO("Era esperado um 'inicio'"),
    FALTA_ENTAO("Era esperado um 'entao'"),
    FALTA_FACA("Era esperado um 'faca'"),
    FALTA_IDENTIFICADOR("Era esperado um Indentificador"),
    FALTA_PONTO_FINAL("'ponto final'(.) não identificado"),
    FALTA_DOIS_PONTOS("Era esperado um 'dois pontos'(:)"),
    FALTA_ABRE_PARENTESES("Era esperado um 'abre parenteses'('(')"),
    FALTA_FECHA_PARENTESES("Era esperado um 'fecha parenteses'(')')"),
    FALTA_NOME_PROGRAMA("Era esperado o nome do programa"),
    FALTA_PONTO_E_VIRGULA("Era esperado um 'Ponto e virgula'(;)"),
    FALTA_VERDADEIRO_OU_FALSO("Era esperado um 'verdadeiro' ou 'falso'"),
    FALTA_VIRGULA_OU_DOIS_PONTO("Era esperado 'virgula'(,) ou 'dois pontos'(:)"),
    
    // Semantico
    PROCEDIMENTO_DUPLICADO("Procedimento com mesmo nome já existente"),
    PROCEDIMENTO_NAO_DECLARADO("Procedimento não declarado"),
    VARIAVEL_DUPLICADA("Variável duplicada"),
    FUNCAO_DUPLICADA("Função com mesmo nome já existente"),
    NAO_DECLARADA("Identificador não existente"),
    VARIAVEL_NAO_DECLARADA("Variável não declarada");
    
    private String descricao;
    
    DescricaoErro(String descricao){
        this.descricao = descricao;
    }
    
    public String getDescricao(){
        return this.descricao;
    }
}
