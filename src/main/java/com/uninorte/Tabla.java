package com.uninorte;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class Tabla {
    private ArrayList<String> structure;
    private ArrayList<Registro> registers;
    private String tableId;
    private Archivo archivo;

    public Tabla(ArrayList<String> structure, String tableId) {
        this.structure = structure;
        this.tableId = tableId;
        this.registers = new ArrayList<>();
        this.archivo = new Archivo(tableId);
    }

    public ArrayList<String> getStructure() {
        return structure;
    }

    public void addReg(Map<String, Object> dataMap, String combinedId,Registro register) {
        registers.add(register);
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(combinedId).append("\n");
        
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("----------------------------");
        archivo.escribir(sb.toString());
        
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void borrarTodo(){
        String archivoRuta = this.archivo.getRuta();
        File archivo = new File(this.archivo.getRuta());
        if (archivo.exists()) {
            if (archivo.delete()) {
                System.out.println("Archivo eliminado: " + archivoRuta);
            } else {
                System.out.println("No se pudo eliminar el archivo: " + archivoRuta);
            }
        } else {
            System.out.println("El archivo no existe: " + archivoRuta);
        }
        structure.clear();
        registers.clear();
    }

    public String getTableId() {
        return tableId;
    }

    public int getNumberOfRegisters(){
        return registers.size();
    }

}
