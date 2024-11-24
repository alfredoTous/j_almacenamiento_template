package com.uninorte;

import java.io.*;
import java.util.ArrayList;

public class BaseDeDatos {

    private static ArrayList<ArrayList<String>> regStructures;
    private static ArrayList<Tabla> tablas;

    public static Tabla getTable(ArrayList<String>structure){
        for (Tabla tabla : tablas) {
            if (tabla.getStructure() == structure){
                return tabla;
            }
        }
        return null;
    }

    public void addRegStructure(Object... data){
        ArrayList<String> structure = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            String dataType = data[i].getClass().getSimpleName().toLowerCase();
            structure.add(dataType);
        }
        regStructures.add(structure);
    }

    public static boolean containsStructure(Object... data){
            ArrayList<String> structure = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                String dataType = data[i].getClass().getSimpleName().toLowerCase();
                structure.add(dataType);
            }
            if(regStructures.contains(structure) == true){
                return true;
            }
            return false;
        }
    
        // Método público para agregar un registro con parámetros variables
        public static String AgregarRegistro(Object... datos) {
            Registro registro = new Registro();
            int dataLength = datos.length;
            ArrayList<String> structure = new ArrayList<>();
            for (int i = 0; i < dataLength; i++){
                String key = datos[i].getClass().getSimpleName().toLowerCase();
                structure.add(key);
                
                int counter = 2; 
                while (registro.getDataMap().containsKey(key)){
                    key = datos[i].getClass().getSimpleName().toLowerCase()+counter;
                }
            registro.addData(key, datos[i]);
            }
            Tabla table;
            String formattedTableId;
            if (regStructures.contains(structure) == false){
                int numberOfTables = ObtenerNumeroTablas();
                if (numberOfTables == -1){
                    numberOfTables = 0;
                }
                int tableId = numberOfTables + 1;
                formattedTableId = String.format("%03d", tableId);
                table = new Tabla(structure,formattedTableId);
            }else{
                table = getTable(structure);
                formattedTableId = table.getTableId();
            }
            int registerId = table.getNumberOfRegisters()+1;
            String formattedRegisterId = String.format("%03d", registerId);
            String combinedId = formattedRegisterId + formattedTableId;
            table.addReg(registro.getDataMap(),combinedId); 
            
        return combinedId; 
    }


    public static String ImprimirTodo() {
        StringBuilder result = new StringBuilder();
    
        return "SomeText";
    }

    public static void BorrarTodo() {
     
    }

    public static boolean EditarReg(String recordId, Object... newValues) {
       
        return false;
    }

    public static boolean BorrarReg(String recordId) {
       
        return false;
    }


    public static int ObtenerNumRegistrosEnTabla(int i) {

        return -1;
    }

    public static Integer ObtenerNumeroTablas() {
        if (tablas == null){
            return -1;
        }
        return tablas.size();
    }

}
