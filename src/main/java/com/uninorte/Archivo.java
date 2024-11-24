package com.uninorte;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Archivo {

    private String nombre;
    private String ruta;

    public Archivo(String nombre) {
        this.nombre = nombre;
        this.ruta = System.getProperty("user.dir") + File.separator + "archivos" + File.separator + this.nombre;
        crearCarpetaSiNoExiste();
    }

    private void crearCarpetaSiNoExiste() {
        File carpeta = new File(System.getProperty("user.dir") + File.separator + "archivos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    public String getRuta() {
        return ruta;
    }

    public void escribir(String texto) {
        try (FileWriter writer = new FileWriter(ruta, true)) {
            writer.write(texto + "\n");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public String leer() {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(ruta));
            return String.join("\n", lineas);
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return "";
    }
}
