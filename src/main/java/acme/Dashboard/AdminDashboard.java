package acme.Dashboard;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.util.Pair;

import acme.datatypes.StatusType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminDashboard implements Serializable{
	
	
	// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
		protected Integer totalNumberOfComponents; 
		protected Map<Pair<String,String>,Double> avgPrize; 
		protected Map<Pair<String,String>,Double> deviationPrize;
		protected Map<Pair<String,String>,Double> maxPrize;
		protected Map<Pair<String,String>,Double> minPrize;
		protected Integer totalNumberOfTools;
		protected Map<String,Double> avgTools;
		protected Map<String,Double> deviationTools;
		protected Map<String,Double> maxTools;
		protected Map<String,Double> minTools;
		protected Map<StatusType,Integer> totalNumberOfPatronages;
		protected Map<StatusType,Double> avgBudget;
		protected Map<StatusType,Double> deviationBudget;
		protected Map<StatusType,Double> maxdBudget;
		protected Map<StatusType,Double> minBudget;
		
	//	Examen -----------------------------------------------------------------
		protected Double ratioOfArtifactsWithBosse;
		protected Map<String, Double> averageBosseBudget;
		protected Map<String, Double> deviationBosseBudget;
		protected Map<String, Double> minBosseBudget;
		protected Map<String, Double> maxBosseBudget;
		
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
