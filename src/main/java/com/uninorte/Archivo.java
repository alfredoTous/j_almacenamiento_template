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
        this.ruta = System.getProperty("user.dir") + File.separator + "archivos" + File.separator + this.nombre + ".txt";
        crearCarpetaSiNoExiste();
    }

    public String getNombre() {
        return nombre;
    }

    private void crearCarpetaSiNoExiste() {
        File carpeta = new File(System.getProperty("user.dir") + File.separator + "archivos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    public void clearFile(){
        try (FileWriter writer = new FileWriter(ruta, false)) {
            
        } catch (IOException e) {
            System.err.println("Error al limpiar el archivo: " + e.getMessage());
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

    public StringBuilder leerRegsArchivo(String archivo){
        try{
            int sw = 1;
            StringBuilder result = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String line;
            StringBuilder register = new StringBuilder();
            while ((line = reader.readLine()) != null ) {
                if(line.startsWith("ID:")){
                    register.append("ID:");
                }
                if(line.equals("----------------------------")){
                    if (register.length() > 0){
                        register.deleteCharAt(register.length()-1);
                        register.deleteCharAt(register.length()-1);
                        result.append(register.toString()).append("--- ");
                        register.setLength(0);
                        sw = 1;
                    } 
                } else{
                    String[] separatedLine = line.split(":");
                    String value = separatedLine[1];
                    register.append(value);
                    sw++;
                    if (sw > 2){
                        register.append(" |");
                    }
                }
            }
            reader.close();
            return result;
        } catch(IOException e){
            System.out.println(e);
            return null;
        }
    }

    public void deleteFile(){
        String rutaArchivo = this.ruta;
        File archivo = new File(rutaArchivo); 
        archivo.delete();
    }
}

// esperado: ID: 001001 true | 0 | Hola de nuevo --- ID: 001002 false | 1 | mundo ----- ID: 002001 false | 1.5
// obtenido: ID: 001001 false | 0 | Hola --- ID: 001002 true | 1 |  mundo ----- ID: 002001 true | 1.5