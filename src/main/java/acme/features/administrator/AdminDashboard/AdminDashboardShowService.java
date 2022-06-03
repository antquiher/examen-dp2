package acme.features.administrator.AdminDashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import acme.Dashboard.AdminDashboard;
import acme.artifact.Artifact;
import acme.artifact.ArtifactType;
import acme.datatypes.StatusType;
import acme.entities.bosse.Bosse;
import acme.entities.patronage.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdminDashboardShowService implements AbstractShowService<Administrator, AdminDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdminDashboardRepository repository;

	// AbstractShowService<Anonymous, Announcement> interface --------------------------

	@Override
	public boolean authorise(final Request<AdminDashboard> request) {
		assert request != null;

		

		return true;
	}

	@Override
	public void unbind(final Request<AdminDashboard> request, final AdminDashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		model.setAttribute("adminDashboard", entity);
		final List<String> currencies = new ArrayList<>();
		currencies.add("EUR");
		currencies.add("USD");
		currencies.add("GBP");
		model.setAttribute("currencies", currencies);
		request.unbind(entity, model, "totalNumberOfComponents", "avgPrize");
	}

	


	@Override
	public AdminDashboard findOne(final Request<AdminDashboard> request) {
		final AdminDashboard result= new AdminDashboard();
		//All properties regarding the components:
		final int n = this.repository.findArtifact(ArtifactType.COMPONENT).size();

		
		final Map<Pair<String, String>, Double> mTechCurr= new HashMap<Pair<String, String>, Double>();
		final Map<Pair<String, String>, Double> mDesviation= new HashMap<Pair<String, String>, Double>();
		final Map<Pair<String, String>, Double> mMax= new HashMap<Pair<String, String>, Double>();
		final Map<Pair<String, String>, Double> mMin= new HashMap<Pair<String, String>, Double>();

		for(final String technology: this.findTechnologies(ArtifactType.COMPONENT)) {
			int nEur= 0;
			int nUsd= 0;
			int nGbp= 0;
			Double totalEur= 0.;
			Double totalUsd= 0.;
			Double totalGbp= 0.;
			
			Double desviationEur= 0.;
			Double desviationUsd= 0.;
			Double desviationGbp= 0.;
			
			final Collection<Artifact> componentList = this.repository.findArtifactTechnologyCurrency(technology, ArtifactType.COMPONENT);
			
			Double maxEur=0.;
			Double maxUsd=0.;
			Double maxGbp=0.;
			Double minEur=Double.MAX_VALUE;
			Double minUsd=Double.MAX_VALUE;
			Double minGbp=Double.MAX_VALUE;
			
			for(final Artifact c: componentList) {
				final Double prize = c.getRetailPrice().getAmount(); 
				switch (c.getRetailPrice().getCurrency()) {
				case "EUR":
					totalEur+=c.getRetailPrice().getAmount();
					nEur++;
					
					maxEur= prize>maxEur?prize:maxEur;
					minEur= prize<minEur?prize:minEur;
					
					break;
				case "USD":
					totalUsd+=c.getRetailPrice().getAmount();
					nUsd++;

					maxUsd= prize>maxUsd?prize:maxUsd;
					minUsd= prize<minUsd?prize:minUsd;
					
					break;
				case "GBP":
					totalGbp+=c.getRetailPrice().getAmount();
					nGbp++;

					maxGbp= prize>maxGbp?prize:maxGbp;
					minGbp= prize<minGbp?prize:minGbp;
					
					break;
				}
				
			}
			for(final Artifact c: componentList) {
				switch (c.getRetailPrice().getCurrency()) {
				case "EUR":
					desviationEur+=(c.getRetailPrice().getAmount() - totalEur!=0?totalEur/nEur:0.)
						  *(c.getRetailPrice().getAmount() - totalEur!=0?totalEur/nEur:0.);
					break;
				case "USD":
					desviationUsd+=(c.getRetailPrice().getAmount() - totalUsd!=0?totalUsd/nUsd:0.)
						  *(c.getRetailPrice().getAmount() - totalUsd!=0?totalUsd/nUsd:0.);
					break;
				case "GBP":
					desviationGbp+=(c.getRetailPrice().getAmount() - totalGbp!=0?totalGbp/nGbp:0.)
						  *(c.getRetailPrice().getAmount() - totalGbp!=0?totalGbp/nGbp:0.);
					break;
				}
			}
			final Pair<String, String> pEur = Pair.of(technology, "EUR");
			final Pair<String, String> pUsd = Pair.of(technology, "USD");
			final Pair<String, String> pGbp = Pair.of(technology, "GBP");
			
			mTechCurr.put(pEur, totalEur!=0?totalEur/nEur:0.);
			mTechCurr.put(pUsd, totalUsd!=0?totalUsd/nUsd:0.);
			mTechCurr.put(pGbp, totalGbp!=0?totalGbp/nGbp:0.);
			mMax.put(pEur, maxEur);
			mMax.put(pUsd, maxUsd);
			mMax.put(pGbp, maxGbp);
			mMin.put(pEur, minEur==Double.MAX_VALUE?0.:minEur);
			mMin.put(pUsd, minUsd==Double.MAX_VALUE?0.:minUsd);
			mMin.put(pGbp, minGbp==Double.MAX_VALUE?0.:minGbp);
			mDesviation.put(pEur, nEur!=0?Math.sqrt(desviationEur/nEur):0);
			mDesviation.put(pUsd, nUsd!=0?Math.sqrt(desviationUsd/nUsd):0);
			mDesviation.put(pGbp, nGbp!=0?Math.sqrt(desviationGbp/nGbp):0);
		}
		
		//All properties regarding the tool:
		final int nTools = this.repository.findArtifact(ArtifactType.TOOL).size();

		
		final Map<String, Double> mTechCurrTools= new HashMap<String, Double>();
		final Map<String, Double> mDesviationTools= new HashMap<String, Double>();
		final Map<String, Double> mMaxTools= new HashMap<String, Double>();
		final Map<String, Double> mMinTools= new HashMap<String, Double>();

		
		int nEur= 0;
		int nUsd= 0;
		int nGbp= 0;
		Double totalEur= 0.;
		Double totalUsd= 0.;
		Double totalGbp= 0.;
		
		Double desviationEur= 0.;
		Double desviationUsd= 0.;
		Double desviationGbp= 0.;
			
		final Collection<Artifact> toolList = this.repository.findArtifact(ArtifactType.TOOL);
		
		Double maxEur=0.;
		Double maxUsd=0.;
		Double maxGbp=0.;
		Double minEur=Double.MAX_VALUE;
		Double minUsd=Double.MAX_VALUE;
		Double minGbp=Double.MAX_VALUE;
		
		for(final Artifact c: toolList) {
			final Double prize = c.getRetailPrice().getAmount(); 
			switch (c.getRetailPrice().getCurrency()) {
			case "EUR":
				totalEur+=c.getRetailPrice().getAmount();
				nEur++;
				
				maxEur= prize>maxEur?prize:maxEur;
				minEur= prize<minEur?prize:minEur;
				
				break;
			case "USD":
				totalUsd+=c.getRetailPrice().getAmount();
				nUsd++;

				maxUsd= prize>maxUsd?prize:maxUsd;
				minUsd= prize<minUsd?prize:minUsd;
				
				break;
			case "GBP":
				totalGbp+=c.getRetailPrice().getAmount();
				nGbp++;

				maxGbp= prize>maxGbp?prize:maxGbp;
				minGbp= prize<minGbp?prize:minGbp;
				
				break;
			}
			
		}
		for(final Artifact c: toolList) {
			switch (c.getRetailPrice().getCurrency()) {
			case "EUR":
				desviationEur+=(c.getRetailPrice().getAmount() - totalEur!=0?totalEur/nEur:0.)
					  *(c.getRetailPrice().getAmount() - totalEur!=0?totalEur/nEur:0.);
				break;
			case "USD":
				desviationUsd+=(c.getRetailPrice().getAmount() - totalUsd!=0?totalUsd/nUsd:0.)
					  *(c.getRetailPrice().getAmount() - totalUsd!=0?totalUsd/nUsd:0.);
				break;
			case "GBP":
				desviationGbp+=(c.getRetailPrice().getAmount() - totalGbp!=0?totalGbp/nGbp:0.)
					  *(c.getRetailPrice().getAmount() - totalGbp!=0?totalGbp/nGbp:0.);
				break;
			}
		}
		
		
		mTechCurrTools.put("EUR", totalEur!=0?totalEur/nEur:0.);
		mTechCurrTools.put("USD", totalUsd!=0?totalUsd/nUsd:0.);
		mTechCurrTools.put("GBP", totalGbp!=0?totalGbp/nGbp:0.);
		mMaxTools.put("EUR", maxEur);
		mMaxTools.put("USD", maxUsd);
		mMaxTools.put("GBP", maxGbp);
		mMinTools.put("EUR", minEur==Double.MAX_VALUE?0.:minEur);
		mMinTools.put("USD", minUsd==Double.MAX_VALUE?0.:minUsd);
		mMinTools.put("GBP", minGbp==Double.MAX_VALUE?0.:minGbp);
		mDesviationTools.put("EUR", nEur!=0?Math.sqrt(desviationEur/nEur):0);
		mDesviationTools.put("USD", nUsd!=0?Math.sqrt(desviationUsd/nUsd):0);
		mDesviationTools.put("GBP", nGbp!=0?Math.sqrt(desviationGbp/nGbp):0);
	
		//All properties regarding the patronages:
			final Map<StatusType, Integer> mPatronage= new HashMap<StatusType, Integer>();
			final Map<StatusType, Double> mAvgPatronage= new HashMap<StatusType, Double>();
			final Map<StatusType, Double> mDesviationPatronage= new HashMap<StatusType, Double>();
			final Map<StatusType, Double> mMaxPatronage= new HashMap<StatusType, Double>();
			final Map<StatusType, Double> mMinPatronage= new HashMap<StatusType, Double>();
			
			for (int i=0; i<3;i++) {
				final Collection<Patronage> patronages= this.repository.findPatronage(StatusType.values()[i]);
				
				final int nPatronage = patronages.size();
				Double totalPatronage= 0.;
				Double maxPatronage= 0.;
				Double minPatronage=0.;
				Double desviationPatronage= 0.;
				if(nPatronage>0) {
				maxPatronage= 0.;
				minPatronage= Double.MAX_VALUE;
				for(final Patronage p:patronages) {
					final double prize= p.getBudget().getAmount();
					
					totalPatronage+=prize;
					maxPatronage= prize>maxPatronage?prize:maxPatronage;
					minPatronage= prize<maxPatronage?prize:maxPatronage;
					
				}
				for(final Patronage p:patronages) {
					final double prize= p.getBudget().getAmount();
					
					desviationPatronage+=(prize - totalPatronage/nPatronage)
						  *(prize - totalPatronage/nPatronage);
				}
				}
				mPatronage.put(StatusType.values()[i], nPatronage);
				mMaxPatronage.put(StatusType.values()[i], maxPatronage);
				mMinPatronage.put(StatusType.values()[i], minPatronage==Double.MAX_VALUE?0.:minPatronage);
				mAvgPatronage.put(StatusType.values()[i], nPatronage!=0?totalPatronage/nPatronage:0);
				mDesviationPatronage.put(StatusType.values()[i], nPatronage!=0?Math.sqrt(desviationPatronage/nPatronage):0);
				}
			
		
		result.setTotalNumberOfComponents(n);
		result.setDeviationPrize(mDesviation);
		result.setAvgPrize(mTechCurr);
		result.setMaxPrize(mMax);
		result.setMinPrize(mMin);
		result.setTotalNumberOfTools(nTools);
		result.setAvgPrize(mTechCurr);
		result.setDeviationTools(mDesviationTools);
		result.setMaxTools(mMaxTools);
		result.setMinTools(mMinTools);
		result.setTotalNumberOfPatronages(mPatronage);
		result.setAvgBudget(mAvgPatronage);
		result.setDeviationBudget(mDesviationPatronage);
		result.setMaxdBudget(mMaxPatronage);
		result.setMinBudget(mMinPatronage);
		
		
	//	Examen ------------------------------------------------------------------------------------------
		
		final int nChimpum = this.repository.findAllChimpums().size();
		final Double nArtifacts = Double.valueOf(this.repository.findArtifact(ArtifactType.COMPONENT).size());	//cambiar en funcion de si es Tool o component
		
		result.setRatioOfArtifactsWithBosse(nArtifacts>0? nChimpum/nArtifacts:0.);
		
		final Map<String, Double> mAverageChimpumBudget= new HashMap<String, Double>();
		final Map<String, Double> mDeviationChimpumBudget= new HashMap<String, Double>();
		final Map<String, Double> mMinChimpumBudget= new HashMap<String, Double>();
		final Map<String, Double> mMaxChimpumBudget= new HashMap<String, Double>();

		
		nEur= 0;
		nUsd= 0;
		nGbp= 0;
		totalEur= 0.;
		totalUsd= 0.;
		totalGbp= 0.;
		
		
		desviationEur= 0.;
		desviationUsd= 0.;
		desviationGbp= 0.;
			
		final Collection<Bosse> chimpumList = this.repository.findAllChimpums();
		
		maxEur=0.;
		maxUsd=0.;
		maxGbp=0.;
		minEur=Double.MAX_VALUE;
		minUsd=Double.MAX_VALUE;
		minGbp=Double.MAX_VALUE;
		
		for(final Bosse c: chimpumList) {
			final Double prize = c.getIncome().getAmount(); 
			switch (c.getIncome().getCurrency()) {
			case "EUR":
				totalEur+=c.getIncome().getAmount();
				nEur++;
				
				maxEur= prize>maxEur?prize:maxEur;
				minEur= prize<minEur?prize:minEur;
				
				break;
			case "USD":
				totalUsd+=c.getIncome().getAmount();
				nUsd++;

				maxUsd= prize>maxUsd?prize:maxUsd;
				minUsd= prize<minUsd?prize:minUsd;
				
				break;
			case "GBP":
				totalGbp+=c.getIncome().getAmount();
				nGbp++;

				maxGbp= prize>maxGbp?prize:maxGbp;
				minGbp= prize<minGbp?prize:minGbp;
				
				break;
			}
			
		}
		for(final Bosse c: chimpumList) {
			switch (c.getIncome().getCurrency()) {
			case "EUR":
				desviationEur+=(c.getIncome().getAmount() - totalEur!=0?totalEur/nEur:0.)
					  *(c.getIncome().getAmount() - totalEur!=0?totalEur/nEur:0.);
				break;
			case "USD":
				desviationUsd+=(c.getIncome().getAmount() - totalUsd!=0?totalUsd/nUsd:0.)
					  *(c.getIncome().getAmount() - totalUsd!=0?totalUsd/nUsd:0.);
				break;
			case "GBP":
				desviationGbp+=(c.getIncome().getAmount() - totalGbp!=0?totalGbp/nGbp:0.)
					  *(c.getIncome().getAmount() - totalGbp!=0?totalGbp/nGbp:0.);
				break;
			}
		}
		
		
		mAverageChimpumBudget.put("EUR", totalEur!=0?totalEur/nEur:0.);
		mAverageChimpumBudget.put("USD", totalUsd!=0?totalUsd/nUsd:0.);
		mAverageChimpumBudget.put("GBP", totalGbp!=0?totalGbp/nGbp:0.);
		mMaxChimpumBudget.put("EUR", maxEur);
		mMaxChimpumBudget.put("USD", maxUsd);
		mMaxChimpumBudget.put("GBP", maxGbp);
		mMinChimpumBudget.put("EUR", minEur==Double.MAX_VALUE?0.:minEur);
		mMinChimpumBudget.put("USD", minUsd==Double.MAX_VALUE?0.:minUsd);
		mMinChimpumBudget.put("GBP", minGbp==Double.MAX_VALUE?0.:minGbp);
		mDeviationChimpumBudget.put("EUR", nEur!=0?Math.sqrt(desviationEur/nEur):0);
		mDeviationChimpumBudget.put("USD", nUsd!=0?Math.sqrt(desviationUsd/nUsd):0);
		mDeviationChimpumBudget.put("GBP", nGbp!=0?Math.sqrt(desviationGbp/nGbp):0);
		result.setAverageBosseBudget(mAverageChimpumBudget);
		result.setMaxBosseBudget(mMaxChimpumBudget);
		result.setMinBosseBudget(mMinChimpumBudget);
		result.setDeviationBosseBudget(mDeviationChimpumBudget);
		
		return result;
	}

	

	private HashSet<String> findTechnologies(final ArtifactType a){
		final HashSet<String> s= new HashSet<String>();
		final Collection<Artifact> lis = this.repository.findArtifact(a);
		for(final Artifact c: lis) {
			s.add(c.getTechnology());			
		}
		return s;
	}
}
