package com.uninorte;

import java.util.LinkedHashMap;
import java.util.Map;

public class Registro {
    private Map<String, Object> dataMap;
    private String registerId;

    public Registro(){
        this.dataMap = new LinkedHashMap<>();
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }
    
    public void addData(String key,Object value){
        dataMap.put(key, value);
    }
}
