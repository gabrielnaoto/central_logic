/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.controller;

import javax.swing.UIManager;

/**
 *
 * @author ignoi
 */
public class Run {

    public static void main(String[] args) {
        /*
        Windows     - com.sun.java.swing.plaf.windows.WindowsLookAndFeel
        Metal       - javax.swing.plaf.metal.MetalLookAndFeel
        GTK         - com.sun.java.swing.plaf.gtk.GTKLookAndFeel
        Aqua        - apple.laf.AquaLookAndFeel
        */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
        }
        WindowController wc = new WindowController();
        wc.start();
    }

}
