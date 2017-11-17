package br.udesc.mps.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("10.60.30.86", 6669);
            DataInputStream inbound = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outbound = new DataOutputStream(clientSocket.getOutputStream());
            TelaClienteController tela = new TelaClienteController(outbound);
            String mesagemRecebida = "";
            String mensagemEncryptada;
            byte[] arrayMensagem = {};
            do {

                if (inbound.available() > 0) {

                    arrayMensagem = new byte[inbound.available()];
                    for (int i = 0; i < arrayMensagem.length; i++) {

                        arrayMensagem[i] = inbound.readByte();
                    }
                    mensagemEncryptada = new String(arrayMensagem);
                    arrayMensagem = Encryptor.encrypt(arrayMensagem);
                    mesagemRecebida = new String(arrayMensagem);
                    if (!mesagemRecebida.isEmpty()) {
                        tela.adicionarMensagemNaTela(mesagemRecebida);
                    }
                }
            } while (tela.isAtiva());
            inbound.close();
            outbound.close();
            clientSocket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
