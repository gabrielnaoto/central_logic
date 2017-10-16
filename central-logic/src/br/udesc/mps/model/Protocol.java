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
public class Protocol {

    private char command; // R W
    private char register; // D M R 
    private int addr; //0-29999
    private int amount; //1-30000
    private Object[] values;

    public Short[] encode() {
        return null;
    }
    
    public Protocol decode(Short[] message) {
        return null;
    }
    
    

}
