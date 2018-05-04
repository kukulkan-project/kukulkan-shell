package mx.infotec.dads.kukulkan.shell.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.infotec.dads.kukulkan.metamodel.foundation.IPlugin;

/**
 * Database Configuration
 * 
 * @author
 *
 */
public class PluginGraphs implements Serializable, IPlugin {

    private static final long serialVersionUID = 1L;

    private String name = "Graphs";

    private  List<GraphType> graphs = new ArrayList<>();;

    public PluginGraphs(){ }
    
    public PluginGraphs(List<GraphType> graphs) {
        this.graphs = graphs;
    }

    public String getName() {
        return name;
    }

    public List<GraphType> getGraphs() { return graphs; }

    public void setGraphs(List<GraphType> graphs) { this.graphs = graphs; }

    public boolean containsGraphs(String graphName) {
        return graphs.contains(graphName);
    }

    public void addGraphs(GraphType... graphsNames) {
        for (GraphType graphName : graphsNames) {
            graphs.add(graphName);
        }
    }

}
