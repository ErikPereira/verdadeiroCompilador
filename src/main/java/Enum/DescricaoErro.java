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
    VIRGULA_ERRADA("'virgula'(,) encontrada no lugar errado"),
    PONTO_VIRGULA_ANTES_SENAO("Não é permitido 'ponto e virgula'(;) antes de um 'senao'"),
    FALTA_TIPO("Era esperado um tipo (inteiro ou booleano)"),
    
    FALTA_INICIO("Erro Encontrado!"
            + "\n\nPossiveis problemas:"
            + "\nElemento 'inicio' era esperado;"
            + "\nElemento 'fim' usado inadequadamente;"
            + "\nVerifique o ultimo elemento da linha anterior."),
    
    FALTA_ENTAO("Era esperado um 'entao'"),
    FALTA_FACA("Era esperado um 'faca'"),
    EXPRESSAO_INCORRETA("Erro na expressao utilizada.\nElemento incorreto: "),
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
    PROCEDIMENTO_DUPLICADO("Identificador já cadastrado"),
    VARIAVEL_DUPLICADA("Variável duplicada"),
    FUNCAO_DUPLICADA("Identificador já cadastrado"),
    NAO_DECLARADA("Identificador não declarado"),
    TIPOS_INCOMPATÍVEIS("Tipo da Atribuição ou Expressão incorreta"),
    NAO_É_FUNCAO("Identificador não é função"),
    NAO_É_VARIAVEL("Identificador não é variavel"),
    PONTO_FINAL_ERRADO("'Ponto final' (.) usado inadequadamente"),
    FUNCAO_SEM_ATRIBUICAO("Erro na atribuiçao da função."
            + "\nPossiveis problemas:"
            + "\nNão existe atribuição\nAtribuição inadequada\nAtribuição não alcançável "),
    
    NAO_É_VARIAVEL_FUNCAO("Identificador não é variavel nem função"),
    VARIAVEL_NAO_DECLARADA("Variável não declarada"),
    FUNCAO_NAO_DECLARADO("Procedimento não declarado"),
    PROCEDIMENTO_NAO_DECLARADO("Procedimento não declarado"),
    ATRIBUI_FUNCAO_FORA_DO_ESCOPO("Atribuição a função inadequada");
    
    private String descricao;
    
    DescricaoErro(String descricao){
        this.descricao = descricao;
    }
    
    public String getDescricao(){
        return this.descricao;
    }
}
