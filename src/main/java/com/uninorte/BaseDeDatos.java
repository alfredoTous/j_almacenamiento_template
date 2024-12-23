package com.uninorte;

import java.util.ArrayList;
import java.util.Map;

public class BaseDeDatos {

    private static ArrayList<ArrayList<String>> regStructures = new ArrayList<>();
    private static ArrayList<Tabla> tablas = new ArrayList<>();
    
        // Método público para agregar un registro con parámetros variables
    public static String AgregarRegistro(Object... datos) {
            Registro registro = new Registro();
            int dataLength = datos.length;
            ArrayList<String> structure = new ArrayList<>();
            for (int i = 0; i < dataLength; i++){
                String key = datos[i].getClass().getSimpleName().toLowerCase();
                structure.add(key);
                
                int counter = 1; 
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
                tablas.add(table);
                regStructures.add(structure);
            }else{
                table = getTable(structure);
                formattedTableId = table.getTableId();
            }

            for (Registro existingReg : table.getRegisters()) {
                if (existingReg.getDataMap().equals(registro.getDataMap())) {
                    return formattedTableId + existingReg.getRegisterId();
                }
            }
            
            int registerId = table.getNumberOfRegisters()+1;
            String formattedRegisterId = String.format("%03d", registerId);
            registro.setRegisterId(formattedRegisterId);
            String combinedId = formattedTableId + formattedRegisterId ;
            table.addReg(registro.getDataMap(),combinedId,registro); 
        return combinedId; 
    }


    public static String ImprimirTodo() {
        StringBuilder result = new StringBuilder();
        for (Tabla tabla : tablas) {
            Archivo tableFile = tabla.getArchivo();
            String archivo = "archivos/"+tableFile.getNombre() + ".txt".trim();
            StringBuilder tableResult = tableFile.leerRegsArchivo(archivo);
            if(tableResult != null && tableResult.length() > 0){
                result.append(tableResult.toString());
                result.append("-- ");
                result.replace(result.length()-7, result.length()-1, " -----");
            }
        }
        result.setLength(result.length()-7);
        return result.toString();
    }

    public static void BorrarTodo() {
        for (Tabla tabla : tablas) {
            tabla.borrarTodo();
        }
        tablas.clear();
        regStructures.clear();
    }

    public static boolean EditarReg(String recordId, Object... newValues) {
       String tableId = recordId.substring(0, 3);
       String registerId = recordId.substring(3, 6);
       Tabla table = getTableFromId(tableId);
       if (table == null){
        return false;
       }
       table.replaceRegister(registerId, newValues);
       ArrayList<Object[]> registersValues = table.getRegistersValues();
       table.borrarContenido();
       for (Object[] objects : registersValues) {
        AgregarRegistro(objects);
       }
       return true;
    }

    public static int ObtenerNumRegistrosEnTabla(int i) {
        String formattedTableId = String.format("%03d", i);
        for (Tabla tabla : tablas) {
            System.out.println(tabla.getTableId());
            if (tabla.getTableId().equals(formattedTableId)){
                return tabla.getNumberOfRegisters();
            }
        }
        return -1;
    }

    public static Integer ObtenerNumeroTablas() {
        if (tablas == null){
            return -1;
        }
        return tablas.size();
    }

    public static boolean BorrarReg(String recordId) {
        String tableId = recordId.substring(0, 3);
        String registerId = recordId.substring(3, 6);
        Tabla tabla = null;
        for (Tabla table : tablas) {
            if(table.getTableId().equals(tableId)){
                tabla = table;
                for (Registro register : table.getRegisters()) {
                    if(register.getRegisterId().equals(registerId)){
                        table.borrarReg(register);
                        break;
                    }
                }
                break;
            }
        }
        if(tabla == null){
            return false;
        }
        ArrayList<Object[]> registersValues = tabla.getRegistersValues();
        tabla.borrarContenido();
        for (Object[] objects : registersValues) {
         AgregarRegistro(objects);
        }
        return true;
    }

    public static Registro obtenerReg(String recordId){
        String tableId = recordId.substring(0, 3);
        String registerId = recordId.substring(3, 6);
        Tabla table = getTableFromId(tableId);
        if (table == null){
            return null;
        }
        for (Registro register : table.getRegisters()) {
            if(register.getRegisterId().equals(registerId)){
                return register;
            }
        }
        return null;
    }

    public static String consultarReg(String recordId){
        Registro reg = obtenerReg(recordId);
        if(reg == null){
            return null;
        }
        Map<String,Object> regDataMap = reg.getDataMap();
        return regDataMap.toString();
    }

    public static Tabla getTable(ArrayList<String>structure){
        for (Tabla tabla : tablas) {
            if (tabla.getStructure().equals(structure)){
                return tabla;
            }
        }
        return null;
    }

    public static Tabla getTableFromId(String tableId){
        for (Tabla tabla : tablas) {
            if (tabla.getTableId().equals(tableId)){
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
}
