package acme.features.inventor.bosse;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bosse.Bosse;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class BosseListService implements AbstractListService<Inventor, Bosse>{
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected BosseRepository repository;

		// AbstractListService<Patron, Bosse>  interface -------------------------


		@Override
		public boolean authorise(final Request<Bosse> request) {
			assert request != null;

			return true;
		}
		
		@Override
		public Collection<Bosse> findMany(final Request<Bosse> request) {
			assert request != null;

			Collection<Bosse> result;

			result = this.repository.findManyBosse();

			return result;
		}
		
		@Override
		public void unbind(final Request<Bosse> request, final Bosse entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "code", "subject", "summary");
		}

}