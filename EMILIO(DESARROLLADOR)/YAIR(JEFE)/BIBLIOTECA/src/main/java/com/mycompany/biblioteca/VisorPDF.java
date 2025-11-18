package com.mycompany.biblioteca;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisorPDF extends JPanel {

    private PDDocument documento;
    private PDFRenderer renderizador;
    private int paginaActual = 0;
    private float zoom = 1.0f;

    private JLabel etiquetaImagen;
    private JScrollPane scroll;
    private JButton btnAnterior, btnSiguiente, btnZoomMas, btnZoomMenos;
    private JLabel lblPagina;

    public VisorPDF(String rutaArchivo) {
        setLayout(new BorderLayout());

        try {
            // Cargar el documento PDF
            documento = PDDocument.load(new File(rutaArchivo));
            renderizador = new PDFRenderer(documento);

            // Crear componentes
            etiquetaImagen = new JLabel();
            scroll = new JScrollPane(etiquetaImagen);
            add(scroll, BorderLayout.CENTER);

            JPanel panelBotones = new JPanel();
            panelBotones.setLayout(new FlowLayout());

            btnAnterior = new JButton("⟨ Anterior");
            btnSiguiente = new JButton("Siguiente ⟩");
            btnZoomMenos = new JButton("– Zoom");
            btnZoomMas = new JButton("+ Zoom");
            lblPagina = new JLabel();

            panelBotones.add(btnAnterior);
            panelBotones.add(btnSiguiente);
            panelBotones.add(btnZoomMenos);
            panelBotones.add(btnZoomMas);
            panelBotones.add(lblPagina);

            add(panelBotones, BorderLayout.SOUTH);

            // Listeners
            btnAnterior.addActionListener(e -> cambiarPagina(-1));
            btnSiguiente.addActionListener(e -> cambiarPagina(1));
            btnZoomMas.addActionListener(e -> cambiarZoom(0.2f));
            btnZoomMenos.addActionListener(e -> cambiarZoom(-0.2f));

            // Mostrar la primera página
            mostrarPagina();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el PDF: " + e.getMessage());
        }
    }

    private void mostrarPagina() {
        try {
            BufferedImage imagen = renderizador.renderImageWithDPI(paginaActual, 100 * zoom);
            etiquetaImagen.setIcon(new ImageIcon(imagen));
            lblPagina.setText("Página " + (paginaActual + 1) + " de " + documento.getNumberOfPages());
            revalidate();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al renderizar página: " + e.getMessage());
        }
    }

    private void cambiarPagina(int cambio) {
        int nuevaPagina = paginaActual + cambio;
        if (nuevaPagina >= 0 && nuevaPagina < documento.getNumberOfPages()) {
            paginaActual = nuevaPagina;
            mostrarPagina();
        }
    }

    private void cambiarZoom(float cambio) {
        zoom += cambio;
        if (zoom < 0.5f) zoom = 0.5f;
        if (zoom > 3.0f) zoom = 3.0f;
        mostrarPagina();
    }

    public void cerrarDocumento() {
        try {
            if (documento != null) documento.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
