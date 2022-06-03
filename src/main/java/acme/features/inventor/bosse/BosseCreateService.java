package acme.features.inventor.bosse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.artifact.Artifact;
import acme.artifact.ArtifactType;
import acme.entities.bosse.Bosse;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class BosseCreateService implements AbstractCreateService<Inventor, Bosse> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected BosseRepository repository;

	// AbstractCreateService<Inventor, Chimpum> interface --------------


	@Override
	public boolean authorise(final Request<Bosse> request) {
		assert request != null;

		return true;
	}

	@Override
	public Bosse instantiate(final Request<Bosse> request) {
		assert request != null;

		Bosse result;

		result = new Bosse();
		result.setArtefact(this.repository.findArtifactList(ArtifactType.COMPONENT).get(0));

		return result;
	}

	@Override
	public void bind(final Request<Bosse> request, final Bosse entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		final Date moment= new Date(System.currentTimeMillis() - 1);

		request.bind(entity, errors, "code", "subject", "summary", "period", "income", "moreInfo");
		
		entity.setCreationMoment(moment);
		
	}

	@Override
	public void validate(final Request<Bosse> request, final Bosse entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(entity.getIncome() != null) {
			errors.state(request, entity.getIncome().getAmount() > 0, "income", "inventor.Chimpum.code.repeated.retailPrice.non-negative");
		}else {
			errors.state(request, entity.getIncome() != null, "income", "inventor.Chimpum.code.repeated.retailPrice.not-null");
		}
		
		
		if(entity.getPeriod() != null) {
			errors.state(request, entity.getPeriod().after(entity.getCreationMoment()), "period", "inventor.Chimpum.period.order-error");
		
			final LocalDateTime startDate = entity.getPeriod().toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDateTime();
		
			final LocalDateTime finishDate = entity.getCreationMoment().toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDateTime();
			errors.state(request, Duration.between(finishDate, startDate).toDays() > 30, "period", "inventor.Chimpum.period.duration-error");
		}
		
		
		
		if(entity.getCode() != null) {
			Bosse ch = this.repository.findAnyBosseByCode(entity.getCode());
			errors.state(request, ch==null || ch.getCode()==entity.getCode(), "code", "inventor.Chimpum.period.code-error");
		}else {
			errors.state(request, entity.getCode()!=null, "code", "inventor.bosse.null-code");
			String[] codeSep = entity.getCode().split("/");
			int mes= Integer.parseInt(codeSep[1]);
			errors.state(request, mes>0 || mes<13, "code", "inventor.bosse.bad-code");
		}
		
	}

	@Override
	public void unbind(final Request<Bosse> request, final Bosse entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "subject", "summary", "period", "income", "moreInfo");
		model.setAttribute("isNew", true);
		List<Artifact> listArt = this.repository.findArtifactList(ArtifactType.COMPONENT);
		Artifact a = new Artifact();
		listArt.add(a);
		model.setAttribute("artifact", listArt);
	}

	@Override
	public void create(final Request<Bosse> request, final Bosse entity) {
		assert request != null;
		assert entity != null;

		final Artifact art=this.repository.findArtifactById(request.getModel().getInteger("artifactId"));
		
		entity.setArtefact(art);
		
		this.repository.save(entity);
	}

}
