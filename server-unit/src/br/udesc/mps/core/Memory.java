package br.udesc.mps.core;

import br.udesc.mps.model.Request;
import br.udesc.mps.model.Response;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author sila
 */
public class Memory {

    private int[] m;
    private int[] d;
    private int[] r;

    public Memory() {
        m = new int[5000];
        d = new int[8000];
        r = new int[30000];
    }

    public Request decode(char[] request) {
        Request r = new Request();
        short[] shorts = new short[request.length];
        for (int i = 0; i < request.length; i++) {
            shorts[i] = (short) request[i];
        }
        boolean stx = false;
        boolean etx = false;
        for (int i = 0; i < shorts.length; i++) {
            if (shorts[i] == 0x02) {
                stx = true;
            }
            if (shorts[i] == 0x03) {
                etx = true;
            }
            if (stx && etx) {
                break;
            }
        }
        if (!(stx && etx)) {
            System.out.println("tratar");
        }
        if (shorts[1] == 0x30) {
            r.setOperation("READ");
            r.setRecorder(Request.getRecoderChar(shorts[2]));
            char[] position = {(char) shorts[3], (char) shorts[4], (char) shorts[5], (char) shorts[6]};
            String pos = String.copyValueOf(position);
            r.setPosition(Integer.parseInt(pos, 16));
            char[] amount = {(char) shorts[7], (char) shorts[8], (char) shorts[9], (char) shorts[10]};
            String amo = String.copyValueOf(amount);
            r.setAmount(Integer.parseInt(amo, 16));
        } else {
            r.setOperation("WRITE");
            r.setRecorder(Request.getRecoderChar(shorts[2]));
            char[] position = {(char) shorts[3], (char) shorts[4], (char) shorts[5], (char) shorts[6]};
            String pos = String.copyValueOf(position);
            r.setPosition(Integer.parseInt(pos, 16));
            char[] amount = {(char) shorts[7], (char) shorts[8], (char) shorts[9], (char) shorts[10]};
            String amo = String.copyValueOf(amount);
            r.setAmount(Integer.parseInt(amo, 16));
            int index = 11;
            Object[] values = new Object[r.getAmount()];
            for (int i = 0; i < values.length; i++) {
                char[] a = new char[r.getRecoderSize()];
                for (int j = 0; j < r.getRecoderSize(); j++) {
                    a[j] = (char) shorts[index];
                    index++;
                }
                values[i] = String.copyValueOf(a);
            }
        }

        return r;

    }
    
     public static short[] encode(Response response) {
        short[] result = null;
        result = new short[10];
        result[0] = 0x02; //stx
        result[1] = (short) response.getStatus(); //status
        if (response.getValues() != null) {
            for (int i = 0; i < response.getValues().length; i++) {

            }
        }
        result[result.length - 3] = 0x03; //etx
        int sum = result.length - 2;
        result[result.length - 2] = (short) sum; // possivelmente tem q preencher isso com 0x00
        result[result.length - 1] = (short) sum; //tem q dividir o sum em 2 e enviar aqui
        return null;
    }

    public synchronized void write(char[] message) throws IOException {
        Request request = decode(message);
        String[] values = request.getValues();
        switch (request.getRecorder()) {
            case 'M': {
                for (int i = request.getPosition(); i < request.getAmount() + request.getPosition(); i++) {
                    m[i] = Integer.parseInt(values[i]);
                }
                break;
            }
            case 'R': {
                for (int i = request.getPosition(); i < request.getAmount() + request.getPosition(); i++) {
                    r[i] = Integer.parseInt(values[i]);
                }
                break;
            }
            case 'D': {
                for (int i = request.getPosition(); i < request.getAmount() + request.getPosition(); i++) {
                    d[i] = Integer.parseInt(values[i]);
                }
                break;
            }

        }
    }

}
