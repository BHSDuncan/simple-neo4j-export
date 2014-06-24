package com.sncorp.neo4j.dataexport.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataNode {
    private List<String> labels = new ArrayList<>();
    private long id;
    private Map<String, String> properties = new HashMap<>();
    
    public List<String> getLabels() {
        return labels;
    }
    
    public long getId() {
        return id;
    }
    
    public Map<String, String> getProperties() {
        return properties;
    }
    
    public void addLabel(String label) {
        this.labels.add(label);
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }
    
}
