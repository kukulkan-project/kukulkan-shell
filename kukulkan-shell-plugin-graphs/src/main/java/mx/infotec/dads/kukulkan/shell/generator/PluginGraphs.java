package mx.infotec.dads.kukulkan.shell.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Database Configuration
 * 
 * @author Clara Fragoso
 *
 */
public class PluginGraphs implements Serializable {

    private static final long serialVersionUID = 1L;

    private  List<GraphType> graphs = new ArrayList<>();

    public PluginGraphs(){ }
    
    public PluginGraphs(List<GraphType> graphs) {
        this.graphs = graphs;
    }

    public String getName() {
        return "Graphs";
    }

    public List<GraphType> getGraphs() { return graphs; }

    public void setGraphs(List<GraphType> graphs) { this.graphs = graphs; }

    public void addGraphs(GraphType... graphsNames) {
        Collections.addAll(graphs, graphsNames);
    }

}
