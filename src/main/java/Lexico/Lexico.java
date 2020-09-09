/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import Lexico.Token;

/**
 *
 * @author Erik Silva
 */
public class Lexico {

    private String programa;
    private int posicaoPrograma = 0;
    private java.util.List<Token> tokens = new ArrayList<>();
   
    public Lexico(String programa) {
        this.programa = programa;
    }

    public char Ler() {
        return programa.charAt(posicaoPrograma++);
    }

}
