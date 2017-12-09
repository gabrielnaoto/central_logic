package br.udesc.mps;

import br.udesc.mps.core.Listener;
import br.udesc.mps.core.Memory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Sila Siebert
 */
public class Server {

    private Memory cs;
    private ServerSocket socket;

    public Server() throws IOException {
        this.socket = new ServerSocket(6666);
        this.cs = new Memory();
    }

    public void init() {
        cs = new Memory();
        try {
            Socket clientSocket;

            while (true) {
                System.out.println("Esperando conexão de um cliente");
                clientSocket = socket.accept();
                System.out.println("Cliente conectou.");
                ObjectInputStream inbound = new ObjectInputStream(clientSocket.getInputStream());
                (new Thread(new Listener(clientSocket, cs))).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server unit = new Server();
        unit.init();
    }
}
