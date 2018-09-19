/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvore;

/**
 *
 * @author luiscarlos
 */
public class Dado {
    private String[] dados;
    
    public Dado(String [] dados){
        this.dados=dados;
    }
    
    public String[] getDados(){
        return this.dados;
    }
    
    public void setDados(String[] dados){
        this.dados=dados;
    }
}
