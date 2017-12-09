/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.controller;

import br.udesc.mps.model.Request;
import br.udesc.mps.model.Response;
import br.udesc.mps.view.TableModel;
import br.udesc.mps.view.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ignoi
 */
public class WindowController {

    private Window window;
    private TableModel tm;
    private Request request;
    private Response response;
    private Client c;

    public WindowController() {
        window = new Window();
        tm = new TableModel();
        request = null;
        response = null;
        c = new Client();

        window.jTable.setModel(tm);
        init();
    }

    private void init() {

        window.jButtonSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (read() != null) {
                    JOptionPane.showMessageDialog(window, "Started request...");
                    try {
                        c.send(encode(request));
                    } catch (IOException ex) {
                        System.out.println("IOException");
                    }
                }
            }
        });

        window.jButtonPrepare.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (window.jComboBoxOperation.getSelectedItem().equals("WRITE")) {
                    if ((int) window.jSpinnerAmount.getValue() > 0) {
                        tm.prepare((int) window.jSpinnerPosition.getValue(), (int) window.jSpinnerAmount.getValue());
                        JOptionPane.showMessageDialog(window, "You can now add values!");
                        window.jButtonSend.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(window, "You must inform an amount bigger than 0.");
                    }
                } else {
                    JOptionPane.showMessageDialog(window, "You must be writting in order to prepare.");
                }
            }
        });

        window.jButtonClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    private Request read() {
        if ((int) window.jSpinnerAmount.getValue() <= 0) {
            JOptionPane.showMessageDialog(window, "You must inform an amount bigger than 0.");
            return null;
        }
        if (window.jComboBoxOperation.getSelectedItem().equals("-")) {
            JOptionPane.showMessageDialog(window, "You must select one option.");
            return null;
        }
        if (window.jComboBoxRecorder.getSelectedItem().equals("-")) {
            JOptionPane.showMessageDialog(window, "You must select one recorder.");
            return null;
        }
        Request request = new Request();
        request.setRecorder(((String) window.jComboBoxRecorder.getSelectedItem()).charAt(0));
        request.setOperation((String) window.jComboBoxOperation.getSelectedItem());
        request.setAmount((int) window.jSpinnerAmount.getValue());
        request.setPosition((int) window.jSpinnerPosition.getValue());
        if (request.getOperation().equals("WRITE")) {
//            request.setValues(tm.getValues());
        }
        this.request = request;
        return request;
    }

    private void write(Response r) {
        this.response = r;
        switch (r.getStatus()) {
            case 0: {
                JOptionPane.showMessageDialog(window, "Not recognized.");
                break;
            }
            case 1: {
                JOptionPane.showMessageDialog(window, "Range is not valid.");
                break;
            }
            case 2: {
                JOptionPane.showMessageDialog(window, "Values are not valid.");
                break;
            }
            case 10: {
                tm.update(request.getPosition(), request.getAmount(), response.getValues());
                JOptionPane.showMessageDialog(window, "Success!");
                break;
            }
        }

    }

    public static Response decodeResponse(short[] response) {
        return null;
    }

    public char[] encode(Request request) {
        short[] result = null;
        if (request.getOperation().equals("READ")) {
            result = new short[14];
            result[0] = 0x02;
            result[1] = 0x30;
            result[2] = request.getRecoderShort();
            String position = Integer.toHexString(request.getPosition());
            for (int i = position.length(); i < 4; i++) {
                position = "0" + position;
            }
            String amount = Integer.toHexString(request.getAmount());
            for (int i = amount.length(); i < 4; i++) {
                amount = "0" + amount;
            }
            result[3] = (short) position.charAt(0);
            result[4] = (short) position.charAt(1);
            result[5] = (short) position.charAt(2);
            result[6] = (short) position.charAt(3);
            result[7] = (short) amount.charAt(0);
            result[8] = (short) amount.charAt(1);
            result[9] = (short) amount.charAt(2);
            result[10] = (short) amount.charAt(3);
            result[11] = 0x03;
            int a = 0;
            for (int i = 0; i < result.length - 3; i++) {
                a += result[i];
            }
            int sum = a;
            String check = Integer.toString(sum);
            result[12] = (short) check.charAt(check.length() - 2);
            result[13] = (short) check.charAt(check.length() - 1);
        } else {
            int n = request.getAmount() * request.getRecoderSize();
            result = new short[14 + n];
            result[0] = 0x02;
            result[1] = 0x30;
            result[2] = request.getRecoderShort();
            String position = Integer.toHexString(request.getPosition());
            for (int i = position.length(); i < 4; i++) {
                position = "0" + position;
            }
            String amout = Integer.toHexString(request.getAmount());
            for (int i = amout.length(); i < 4; i++) {
                amout = "0" + amout;
            }
            result[3] = (short) position.charAt(0);
            result[4] = (short) position.charAt(1);
            result[5] = (short) position.charAt(2);
            result[6] = (short) position.charAt(3);
            result[7] = (short) amout.charAt(0);
            result[8] = (short) amout.charAt(1);
            result[9] = (short) amout.charAt(2);
            result[10] = (short) amout.charAt(3);
            int index = 11;
            String[] values = request.getValues();
            for (int i = 0; i < request.getAmount(); i++) {
                String value = (String) values[i];
                switch (request.getRecorder()) {
                    case 'M': {
                        break;
                    }
                    case 'R': {
                        for (int p = value.length(); p < 4; p++) {
                            value = "0" + value;
                        }
                        break;
                    }
                    case 'D': {
                        for (int p = value.length(); p < 8; p++) {
                            value = "0" + value;
                        }
                        break;
                    }

                }
                for (int j = 0; j < request.getRecoderSize(); j++) {
                    result[index] = (short) value.charAt(j);
                    index++;
                }
            }
            result[result.length - 3] = 0x03;
            int a = 0;
            for (int i = 0; i < result.length - 3; i++) {
                a += result[i];
            }
            int sum = a;
            String check = Integer.toString(sum);
        }
        char[] chars = new char[result.length];
        for (int i = 0; i < result.length; i++) {
            chars[i] = (char) result[i];
        }
        return chars;
    }

    private void clear() {
        window.jSpinnerAmount.getModel().setValue(0);
        window.jSpinnerPosition.getModel().setValue(0);
        window.jComboBoxOperation.setSelectedIndex(0);
        window.jComboBoxRecorder.setSelectedIndex(0);
        request = null;
        response = null;
        tm.clear();
        JOptionPane.showMessageDialog(window, "Success! You've successfully finished our training.");
    }

    private void lock() {
        window.jButtonSend.setEnabled(false);
        window.jButtonPrepare.setEnabled(false);
    }

    public void start() {
        window.setVisible(true);
    }

}
