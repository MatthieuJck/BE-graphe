package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.Label;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.utils.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	protected int nbSommetsVisites;
	protected int nbSommets;

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
		this.nbSommetsVisites = 0;
	}

	@Override
	protected ShortestPathSolution doRun() {
		boolean fini = false;
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();
		int graphSize = graph.size();
		ShortestPathSolution solution = null;

		/* Tableau de Labels */
		Label tabLabels[] = new Label [graphSize];

		/* Tas de Labels */
		BinaryHeap<Label> tas = new BinaryHeap<Label>();

		/* Tableau des prédecesseurs */
		Arc[] predecessorArcs = new Arc[graphSize];

		/* Ajout du sommet de départ */
		Label depart = newLabel(data.getOrigin(), data);
		tabLabels[depart.getNode().getId()] = depart;
		tas.insert(depart);
		depart.setInTas();
		depart.setCost(0);

		/* Notifie les observateurs du premier évènement (départ de l'origine) */
		notifyOriginProcessed(data.getOrigin());

		/* Algorithme */
		/* Tant qu'il existe des sommets non marqués */
		while(!tas.isEmpty() && !fini){      	

			Label current= tas.deleteMin();
			/* On indique aux observateurs que le Node a été marqué */
			notifyNodeMarked(current.getNode());
			current.setMark();
			/* Quand on a atteint la destination, on s'arrête */
			if (current.getNode() == data.getDestination()) {
				fini = true;
			}

			/* Parcours des successeurs du sommet courant */
			Iterator<Arc> arc = current.getNode().iterator();
			while (arc.hasNext()) {
				Arc arcIter = arc.next();

				// On vérifie que l'on peut réellement prendre cet arc
				if (!data.isAllowed(arcIter)) {
					continue;
				}

				Node successeur = arcIter.getDestination();
				Label successeurLabel = tabLabels[successeur.getId()];

				/* Creation du label */
				if (successeurLabel == null) {
					/* On informe les observateurs que l'on atteint un Node */
					notifyNodeReached(arcIter.getDestination());
					successeurLabel = newLabel(successeur, data);
					tabLabels[successeurLabel.getNode().getId()] = successeurLabel;
					// a verifier
					tas.insert(successeurLabel);
					this.nbSommetsVisites++;
				}

				/* Si le successeur n'est pas encore marqué */
				if (!successeurLabel.isMark()) {

					/* Vérification de l'amélioration du coût */
					if((successeurLabel.getCost()>(current.getCost()+data.getCost(arcIter))) || (successeurLabel.getCost()==Float.POSITIVE_INFINITY)){
						/* Changement du coût */
						successeurLabel.setCost(current.getCost()+(float)data.getCost(arcIter));
						successeurLabel.setPere(current.getNode());
						
						
						/* Si le label est déjà dans le tas */
						/* On met à jour sa position dans le tas */
						if(successeurLabel.isInTas()) {
							tas.remove(successeurLabel);
						}
						/* Sinon on l'ajoute dans le tas */
						else {
							successeurLabel.setInTas();
						}
						
						
						tas.insert(successeurLabel);
						predecessorArcs[arcIter.getDestination().getId()] = arcIter;

					}
				}
			}
		}

		/* La destination n'a pas de predecesseur, pas de solution */
		if (predecessorArcs[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {

			/* La destination a été trouvée, on notifie les observateurs (?) */
			notifyDestinationReached(data.getDestination());

			/* Créé le path avec les predecessors */
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = predecessorArcs[data.getDestination().getId()];

			while (arc != null) {
				arcs.add(arc);
				arc = predecessorArcs[arc.getOrigin().getId()];
			}

			/* Remet les arcs dans le bon ordre */
			Collections.reverse(arcs);

			/* Créé la solution finale */
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));

		}

		return solution;
	}

	
	public Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}

	public int getNbSommetsVisites() {
		return this.nbSommetsVisites;
	}

}
