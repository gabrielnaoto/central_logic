/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.model;

/**
 *
 * @author gabrielnaoto
 */
public class MemoryManagementUnit {

    //0 ou 1 tinint
    private boolean[] register_M;
    
    //short	2 byte	-32.768	32.767
    private short[] register_D;
    
    //2 caracteres
    private String[] register_R;
    
    public MemoryManagementUnit() {
        register_M = new boolean[5000];
        register_D = new short[8000];
        register_R = new String[30000];
    }
    
    public void write(Protocol message){
        
    }
    
    public Protocol read(){
        return null;
    }

  

}
