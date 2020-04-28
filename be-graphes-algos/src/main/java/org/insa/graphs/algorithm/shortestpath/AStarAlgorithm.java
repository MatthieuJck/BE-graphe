package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.LabelStar;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }


    public Label newLabel(Node noeud, ShortestPathData data) {
    	return new LabelStar(noeud,data);
    }
}
