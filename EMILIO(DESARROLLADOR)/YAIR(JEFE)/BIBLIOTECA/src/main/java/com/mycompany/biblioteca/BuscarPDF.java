package com.mycompany.biblioteca;

import java.io.*;
import java.awt.Desktop;
import java.util.Scanner;

public class BuscarPDF {
    public static void main(String[] args) {
        File carpetaLibros = new File("libros");

        if (!carpetaLibros.exists() || !carpetaLibros.isDirectory()) {
            System.out.println("La carpeta 'libros' no existe.");
            return;
        }

        // Obtenemos todos los archivos PDF en la carpeta
        File[] listaLibros = carpetaLibros.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

        if (listaLibros == null || listaLibros.length == 0) {
            System.out.println("No hay libros en la carpeta 'libros'.");
            return;
        }

        // Mostramos un men con los libros
        System.out.println("Lista de libros disponibles:");
        for (int i = 0; i < listaLibros.length; i++) {
            System.out.println((i + 1) + ". " + listaLibros[i].getName());
        }

        // Pedimos al usuario que elija un libro
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nElige el número del libro que deseas abrir: ");
        int opcion = scanner.nextInt();

        if (opcion < 1 || opcion > listaLibros.length) {
            System.out.println("Opción inválida.");
            return;
        }

        File libroSeleccionado = listaLibros[opcion - 1];
        System.out.println("Libro seleccionado: " + libroSeleccionado.getName());

        // Intentamos abrir el libro
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(libroSeleccionado);
            } else {
                System.out.println("No se puede abrir el archivo automáticamente en este sistema.");
            }
        } catch (IOException e) {
            System.out.println("Error al abrir el libro: " + e.getMessage());
        }
    }
}

