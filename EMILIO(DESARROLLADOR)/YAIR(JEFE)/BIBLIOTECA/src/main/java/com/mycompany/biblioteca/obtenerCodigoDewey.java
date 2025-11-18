package com.mycompany.biblioteca;

public class obtenerCodigoDewey {

    public static String generar(String nombreLibro) {
        nombreLibro = nombreLibro.toLowerCase();

        if (nombreLibro.contains("programacion") || nombreLibro.contains("java") || nombreLibro.contains("python")) {
            return "005";
        } else if (nombreLibro.contains("computacion")) {
            return "004";
        } else if (nombreLibro.contains("historia")) {
            return "900";
        } else if (nombreLibro.contains("quimica")) {
            return "540";
        } else if (nombreLibro.contains("matematicas")) {
            return "510";
        } else if (nombreLibro.contains("biologia")) {
            return "570";
        } else if (nombreLibro.contains("arte")) {
            return "700";
        } else if (nombreLibro.contains("filosofia")) {
            return "100";
        } else {
            return "860"; // general o desconocido
        }
    }
}
