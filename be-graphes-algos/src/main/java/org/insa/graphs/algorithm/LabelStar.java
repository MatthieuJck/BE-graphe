package org.insa.graphs.algorithm;

import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.algorithm.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label{

	private float inf;

	public LabelStar(Node noeud, ShortestPathData data) {
		super(noeud);

		if (data.getMode() == AbstractInputData.Mode.LENGTH) {  // en distance
			this.inf = (float)Point.distance( noeud.getPoint() , data.getDestination().getPoint() ) ; 
		}
		else { // en temps
			int vitesse = Math.max( data.getMaximumSpeed() , data.getGraph().getGraphInformation().getMaximumSpeed() ); 
			this.inf = (float)Point.distance( noeud.getPoint() , data.getDestination().getPoint())/(vitesse*1000.0f/3600.0f ); 
		}
		 
	}

	@Override
	/* coût origine -> noeud + coût à vol d'oiseau noeud -> destination */
	public float getTotalCost() {
		return this.inf + this.cost;
	}


	
}
