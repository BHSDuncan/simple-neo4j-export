package com.sncorp.neo4j.dataexport.entities;

import java.util.List;

public class DataGraph {
    private List<DataNode> nodes;
    private List<DataRelationship> relationships;

    public List<DataNode> getNodes() {
        return nodes;
    }
    
    public List<DataRelationship> getRelationships() {
        return relationships;
    }
    
    public void setNodes(List<DataNode> allNodes) {
        this.nodes = allNodes;
    }
    
    public void setRelationships(List<DataRelationship> allRels) {
        this.relationships = allRels;
    }    
}
