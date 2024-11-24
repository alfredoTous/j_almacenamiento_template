package com.uninorte;

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

    public void addReg(Map<String,Object> dataMap,String combinedId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addReg'");
    }

    public String getTableId() {
        return tableId;
    }

    public int getNumberOfRegisters(){
        return registers.size();
    }

}
