/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.client.newpackage.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import trabalhofinal.Encryptor;
import trabalhofinal.view.InterfaceCliente;
import trabalhofinal.view.Login;

/**
 *
 * @author sila
 */
public class ClientController {

    private Login login;
    private InterfaceCliente intC;
    private DataOutputStream outbound;
    private boolean ativa;
    private String nome;

    public ClientController(DataOutputStream out) {
        this.intC = new InterfaceCliente();
        this.login = new Login(intC, true);
        this.outbound = out;
        inicializarComponentes();
        login.setVisible(true);
        intC.setVisible(true);
        ativa = true;
    }

    public void inicializarComponentes() {
        intC.btEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mandarMensagem();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        login.btLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nome = login.edUsername.getText();
                login.setVisible(false);
            }
        });
    }

    public void mandarMensagem() throws IOException {
        LocalTime horaAtual = LocalTime.now();
        int horas = horaAtual.getHour();
        int minutos = horaAtual.getMinute();
        String min = String.valueOf(minutos);
        if (minutos < 10) {
            min = "0" + minutos;
        }
        int segundos = horaAtual.getSecond();
        String mensagem = horas + ":" + min + ":" + segundos + " -" + nome + "- says:" + intC.edMensagem.getText();
        if (mensagem.contains("adieu")) {
            ativa = false;
        }
        byte[] buffer = mensagem.getBytes();
        buffer = Encryptor.encrypt(buffer);
        outbound.write(buffer, 0, buffer.length);
        intC.edMensagem.setText("");
    }

    public void adicionarMensagemNaTela(String mensagem) {
        String textoMostrado = intC.taMensagens.getText();
        intC.taMensagens.setText(textoMostrado + "\n" + mensagem);
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

}
