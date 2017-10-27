/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.model;

import java.nio.ByteBuffer;
import java.util.ArrayList;

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
        ArrayList<Byte> commands = new ArrayList<Byte>();
        commands.add((byte) 0x02);
        commands.add((byte) command);
        commands.add((byte) register);
//        commands.add((byte) addr1);
//        commands.add((byte) addr2);

        return null;
    }

    public Protocol decode(Short[] message) {
        return null;
    }

    public byte[] intToProtocolFormat(int number) {
        System.out.println();
        String hexNumber = Integer.toHexString(number);

        int decimal = Integer.parseInt(hexNumber, 16);
        System.out.println("Decimal value is " + decimal);

        String hex = Integer.toHexString(number);
        System.out.println("Hex value is " + hex);

        byte[] bytes = ByteBuffer.allocate(4).putInt(number).array();
        byte[] parts = new byte[2];
        for (byte b : bytes) {
            String encoded
                    = String.format("0x%x ", b);
            System.out.println(encoded);
        }
        return null;
    }

    public static void main(String[] args) {
        Protocol p = new Protocol();
        System.out.println(p.intToProtocolFormat(8000));
        System.out.println(p.intToProtocolFormat(30000));

    }
}
