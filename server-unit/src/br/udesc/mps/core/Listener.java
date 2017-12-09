package br.udesc.mps.core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Listener implements Runnable {

    private Socket socket;
    private Memory cs;

    public Listener(Socket conexao, Memory b) {
        super();
        this.socket = conexao;
        this.cs = b;
    }

    public void run() {
        try {
            System.out.println("iniciou");

            ObjectInputStream inbound;
            ObjectOutputStream outbound;

            inbound = new ObjectInputStream(this.socket.getInputStream());
            outbound = new ObjectOutputStream(this.socket.getOutputStream());

            while (true) {
                if (inbound.available() > 0) {
                    System.out.println("Inbound available " + inbound.available());
                    Object o = inbound.readObject();
                    char[] response = null;
                    if (true) { //verificar se a mensagem esta ok
                        cs.write((char[]) o);
                        //montar response
                    } else {
                        //montar response de erro
                    }
                    outbound.writeObject(response);
                }
                Thread.sleep(5000);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
