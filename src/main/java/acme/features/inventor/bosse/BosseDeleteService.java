package acme.features.inventor.bosse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bosse.Bosse;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class BosseDeleteService implements AbstractDeleteService<Inventor, Bosse> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected BosseRepository repository;


	@Override
	public boolean authorise(final Request<Bosse> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public void validate(final Request<Bosse> request, final Bosse entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if(entity.getIncome() != null) {
			errors.state(request, entity.getIncome().getAmount() > 0, "income", "inventor.Chimpum.code.repeated.retailPrice.non-negative");
		}
		else {
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
			errors.state(request, ch==null, "code", "inventor.Chimpum.period.code-error");
			String[] codeSep = entity.getCode().split("/");
			int mes= Integer.parseInt(codeSep[1]);
			errors.state(request, mes>0 || mes<13, "code", "inventor.bosse.bad-code");
			
		}else {
			errors.state(request, entity.getCode()!=null, "code", "inventor.bosse.null-code");
		}
		
	}

	@Override
	public void bind(final Request<Bosse> request, final Bosse entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "creationMoment", "subject", "summary", "period", "income", "moreInfo");
		
	}

	@Override
	public void unbind(final Request<Bosse> request, final Bosse entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "subject", "summary", "period", "income", "moreInfo");
	}

	@Override
	public Bosse findOne(final Request<Bosse> request) {
		assert request != null;
		
		Bosse result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneBosseById(id);

		return result;
	}

	@Override
	public void delete(Request<Bosse> request, Bosse entity) {
		assert request != null;
		assert entity != null;
		
		
		this.repository.delete(entity);
		
	}

	

}
