package acme.features.any.chirp;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chirp.Chirp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class ChirpListService implements AbstractListService<Any, Chirp>{
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected ChirpRepository repository;

		// AbstractListService<Anonymous, Announcement>  interface -------------------------


		@Override
		public boolean authorise(final Request<Chirp> request) {
			assert request != null;

			return true;
		}
		
		@Override
		public Collection<Chirp> findMany(final Request<Chirp> request) {
			assert request != null;

			Collection<Chirp> result;

			result = this.repository.findManyChirp();

			return result;
		}
		
		@Override
		public void unbind(final Request<Chirp> request, final Chirp entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "body", "title");
		}

}
