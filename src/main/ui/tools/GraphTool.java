package ui.tools;

import ui.display.DataFileDisplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//tool used to graph data in RNAseq output file
public class GraphTool extends Tool implements ActionListener {

    //MODIFIES: this
    //EFFECTS: instantiates new GraphTool object
    public GraphTool(ToolBar parent) {
        super(parent, "graph");
        button.addActionListener(this);
    }

    @Override
    //EFFECTS: Signals rnaseq display to create a graph of its data
    public void actionPerformed(ActionEvent e) {
        DataFileDisplay display = toolBar.getDataFileDirectoryMenu().getDataFileDisplay();
        display.getVisualPanel().createGraph();
    }
}
