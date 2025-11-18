package com.mycompany.biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaVisor extends JFrame {

    private VisorPDF visor;

    public VentanaVisor(String rutaPDF) {
        setTitle("Visor de Libro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        visor = new VisorPDF(rutaPDF);
        add(visor, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                visor.cerrarDocumento();
            }
        });
    }
}
