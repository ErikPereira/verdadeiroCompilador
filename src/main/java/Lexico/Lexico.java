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

    private java.util.List<String> programa;
    private java.util.List<Token> tokens = new ArrayList<>();
   
    public Lexico(java.util.List<String> programa) {
        this.programa = programa;
    }

    public void testeOi() {
        JOptionPane.showMessageDialog(null, "Ola meus amigos");
    }

}
