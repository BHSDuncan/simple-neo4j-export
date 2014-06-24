package com.sncorp.neo4j.dataexport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.server.plugins.Description;
import org.neo4j.server.plugins.Name;
import org.neo4j.server.plugins.PluginTarget;
import org.neo4j.server.plugins.ServerPlugin;
import org.neo4j.server.plugins.Source;
import org.neo4j.tooling.GlobalGraphOperations;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sncorp.neo4j.dataexport.entities.DataGraph;
import com.sncorp.neo4j.dataexport.entities.DataNode;
import com.sncorp.neo4j.dataexport.entities.DataRelationship;

@Description("An extension for exporting data out of Neo4j.")
public class DataExport extends ServerPlugin {
    @Name("export_data")
    @Description("Exports node and relationship data in a very basic manner.")
    @PluginTarget(GraphDatabaseService.class)
    public String exportData(@Source GraphDatabaseService gdb) {
        String exportedData = "";
        
        DataGraph dataGraph = new DataGraph();
        List<DataNode> allNodes = new ArrayList<DataNode>();
        List<DataRelationship> allRels = new ArrayList<DataRelationship>();
                
        // TODO: To implement streaming, use Jackson lib; however, will need to stream out to JsonGenerator
        // as nodes and relationships are iterated through.  This is different than assembling the POJOs and dumping 
        // them to file.
        try (Transaction tx = gdb.beginTx()) {
            for (Node node : GlobalGraphOperations.at(gdb).getAllNodes()) {
                allNodes.add(this.createDataNode(node));                
            } // for

            for (Relationship rel : GlobalGraphOperations.at(gdb).getAllRelationships()) {
                allRels.add(this.createDataRel(rel));
            } // for
        } // try
        
        dataGraph.setNodes(allNodes);
        dataGraph.setRelationships(allRels);
        
        // TODO: Replace this call with "exportAsFile".
        exportedData = this.createExportDocument(dataGraph);
        
        return exportedData;
    } // exportData
    
    // Not currently used.  To be called once file writing is implemented.
    private void exportAsFile(DataGraph dataGraph) {
        String exportDocument = "";
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            // TODO: Insert filename here. Get it from config file.
            mapper.writeValue(new File(""), dataGraph);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } // try        
    } // exportAsFile
        
    private String createExportDocument(DataGraph dataGraph) {
        String exportDocument = "";
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            exportDocument = mapper.writeValueAsString(dataGraph);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } // try
        
        return exportDocument;
    } // createExportDocument
    
    private DataNode createDataNode(Node node) {
        DataNode dataNode = new DataNode();
        
        dataNode.setId(node.getId());
                
        for (Label label : node.getLabels()) {
            dataNode.addLabel(label.name());
        } // for
        
        for (String key : node.getPropertyKeys()) {
            try {
                dataNode.setProperty(key, node.getProperty(key).toString());
            } catch (Exception e) {
                e.printStackTrace();
            } // try
        } // for
        
        return dataNode;
    } // createDataNode
    
    private DataRelationship createDataRel(Relationship rel) {
        DataRelationship dataRel = new DataRelationship();
        
        dataRel.setId(rel.getId());
        dataRel.setRelType(rel.getType().name());
        
        dataRel.setStartNodeId(rel.getStartNode().getId());
        dataRel.setEndNodeId(rel.getEndNode().getId());

        for (String key : rel.getPropertyKeys()) {
            try {
                dataRel.setProperty(key, rel.getProperty(key).toString());
            } catch (Exception e) {
                e.printStackTrace();
            } // try
        } // for
        
        return dataRel;
    } // createDataRel
}
