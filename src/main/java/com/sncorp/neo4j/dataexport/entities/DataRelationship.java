package com.sncorp.neo4j.dataexport.entities;

import java.util.Map;

public class DataRelationship {
    private long id = 0;
    private String relType = "";
    private Map<String, String> properties;
    private long startNodeId = 0;
    private long endNodeId = 0;
    
    public long getId() {
        return id;
    }
    
    public String getRelType() {
        return relType;
    }
    
    public Map<String, String> getProperties() {
        return properties;
    }

    public long getStartNodeId() {
        return startNodeId;
    }

    public long getEndNodeId() {
        return endNodeId;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setRelType(String relType) {
        this.relType = relType;
    }
    
    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public void setStartNodeId(long startNodeId) {
        this.startNodeId = startNodeId;
    }

    public void setEndNodeId(long endNodeId) {
        this.endNodeId = endNodeId;
    }
}
