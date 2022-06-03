package acme.features.inventor.bosse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.artifact.Artifact;
import acme.artifact.ArtifactType;
import acme.entities.bosse.Bosse;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class BosseShowService implements AbstractShowService<Inventor, Bosse>{
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected BosseRepository repository;

		// AbstractShowService<Inventor, Bosse> interface --------------------------

		@Override
		public boolean authorise(final Request<Bosse> request) {
			assert request != null;

			return true;
		}

		@Override
		public void unbind(final Request<Bosse> request, final Bosse entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "code", "creationMoment", "subject", "summary", "period", "income", "moreInfo");
			model.setAttribute("isNew", false);
			List<Artifact> listArt = this.repository.findArtifactList(ArtifactType.COMPONENT);
			Artifact a = new Artifact();
			listArt.add(0, a);
			a = entity.getArtefact();
			if(entity.getArtefact() != null) {
				listArt.add(0, a);
			}
			
			model.setAttribute("artifact", listArt);
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

		

}
