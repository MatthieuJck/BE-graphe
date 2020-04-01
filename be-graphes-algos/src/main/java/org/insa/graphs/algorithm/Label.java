package org.insa.graphs.algorithm;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {
	protected float cost;
	private boolean mark; // vrai si noeud marqué
	private Node pere;
	private Node node;
	private boolean inTas; // vrai si noeud dans le tas
	private Arc arcPere;
	
	public Label(Node noeud){
		this.node = noeud;
		this.mark = false;
		this.cost = Float.POSITIVE_INFINITY;
		this.pere = null; 
		this.inTas = false;
		this.arcPere = null;
	}
	

	public float getCost() {
		return cost;
	}

	public void setCost(float cout) {
		this.cost = cout;
	}

	public boolean isMark() {
		return mark;
	}

	public void setMark() {
		this.mark = true;
	}

	public Node getPere() {
		return pere;
	}

	public void setPere(Node pere) {
		this.pere = pere;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public boolean isInTas() {
		return inTas;
	}

	public void setInTas() {
		this.inTas = true;
	}

	public Arc getArcPere() {
		return arcPere;
	}

	public void setArcPere(Arc arcPere) {
		this.arcPere = arcPere;
	}

	@Override
	public int compareTo(Label autre) {
		int resultat;
		if (this.getCost() < autre.getCost()) {
			resultat = -1;
		}
		else if (this.getCost() == autre.getCost()) {
			resultat = 0;
		}
		else {
			resultat = 1;
		}
		return resultat;
	}

}
