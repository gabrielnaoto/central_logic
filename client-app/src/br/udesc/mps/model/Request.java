/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.model;

/**
 *
 * @author ignoi
 */
public class Request {

    private String operation;
    private char recorder;
    private int position;
    private int amount;
    private String[] values;

    public Request() {
    }

    public Request(String operation, char recorder, int position, int amount, String[] values) {
        this.operation = operation;
        this.recorder = recorder;
        this.position = position;
        this.amount = amount;
        this.values = values;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public char getRecorder() {
        return recorder;
    }

    public void setRecorder(char recorder) {
        this.recorder = recorder;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public int getRecoderSize() {
        if (recorder == 'M') {
            return 1;
        }
        if (recorder == 'D') {
            return 2;
        }
        if (recorder == 'R') {
            return 4;
        }
        return 0;
    }

    public short getRecoderShort() {
        if (recorder == 'M') {
            return 0x4D;
        }
        if (recorder == 'D') {
            return 0x44;
        }
        if (recorder == 'R') {
            return 0x52;
        }
        return 0x00;
    }
    
     public static char getRecoderChar(short a) {
        if (a == 0x4D) {
            return 'M';
        }
        if (a == 0x44) {
            return 'D';
        }
        if (a == 0x52) {
            return 'R';
        }
        return '0';
    }

    public short getRecoder() {
        if (recorder == 'M') {
            return 0x4D;
        }
        if (recorder == 'D') {
            return 0x44;
        }
        if (recorder == 'R') {
            return 0x52;
        }
        return 0x00;
    }

    @Override
    public String toString() {
        String vx = "[";
        for (String value : values) {
            vx += value + ", ";
        }
        vx += "]";
        return "Request{" + "operation=" + operation + ", recorder=" + recorder + ", position=" + position + ", amount=" + amount + ", values=" + vx + '}';
    }

}
