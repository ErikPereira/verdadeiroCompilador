package Posfixa;

import java.util.ArrayList;
import java.util.List;

public class Posfixa {
    
    public String lista;
    public List<analisaPosfixa> pilha;

    public int nivelElementoAnterior;
    public String elementoAnterior;
 

    public Posfixa() {
        this.nivelElementoAnterior = -1;
        this.lista = "";
        this.pilha = new ArrayList<>(); 
    }

    public void insereLista(String elemento){
        if(!elemento.equals("(")){
            lista +=elemento+" ";
        }
    }
    
    public void inserePilha(String elemento, int nivel){        
  
        if(nivelElementoAnterior >= nivel && nivelElementoAnterior!=10){  
          
            int i = pilha.size()-1;
            while(pilha.get(i).getNivel()>=nivel){
                if(pilha.get(i).getNivel()==10)break;
                
                insereLista(pilha.get(i).getElemento());
                pilha.remove(i);
                i--;
                if(i==-1)break;              
            }           
            if(nivel == 7){
                switch(elemento){
                    case "-":
                        elemento = "-u";
                        break;
                    case "+":
                        elemento = "+u";
                        break;
                }
                
            }
            pilha.add(new analisaPosfixa(elemento,nivel));            
        }       
        else{
                   
            pilha.add(new analisaPosfixa(elemento,nivel)); 
        }       
        nivelElementoAnterior = nivel;
        elementoAnterior = elemento;
    }
    
    public void fechaParenteses(){
      
        int i =pilha.size()-1;
        do{
            
            insereLista(pilha.get(i).getElemento());
            pilha.remove(i);            
            i--;
        }while(!pilha.get(i).getElemento().equals("("));
        pilha.remove(i); 
        if(pilha.isEmpty()) nivelElementoAnterior = -1;
    }
    
    public void fimExpressao(){    
        if(!pilha.isEmpty()){
     
            for(int i= pilha.size();i>0;i--){            
                insereLista(pilha.get(i-1).getElemento());
                pilha.remove(i-1);           
            }       
        }
        nivelElementoAnterior = -1;
    }
    
    public void printaLista(){
    
        System.out.println(lista);
     
    }
    
}