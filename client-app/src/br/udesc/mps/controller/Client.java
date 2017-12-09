/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sila Siebert
 */
public class Client {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 6666;

    private Socket clientSocket;
    private ObjectInputStream inbound;
    private ObjectOutputStream outbound;

    public Client() {
        try {
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            inbound = new ObjectInputStream(clientSocket.getInputStream());
            outbound = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void send(char[] message) throws IOException {
        outbound.writeObject(message);
    }

    public ObjectInputStream getInbound() {
        return inbound;
    }

//    public void awaitResponse() throws IOException {
//        Thread receiver = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                byte[] arrayMensagem = {};
//                do {
//                    try {
//                        if (inbound.available() > 0) {
//                            try {
//                                arrayMensagem = new byte[inbound.available()];
//                                for (int i = 0; i < arrayMensagem.length; i++) {
//                                    arrayMensagem[i] = inbound.readByte();
//                                }
//                                System.out.println("Resposta recebida");
//                                for (byte b : arrayMensagem) {
//                                    System.out.println(b);
//                                }
//                            } catch (IOException ex) {
//                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                        Thread.sleep(5000);
//                    } catch (Exception ex) {
//                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } while (true);
//            }
//        });
//        receiver.start();
//
//    }

}
