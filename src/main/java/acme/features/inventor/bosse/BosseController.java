package acme.features.inventor.bosse;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.bosse.Bosse;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class BosseController extends AbstractController<Inventor, Bosse>{
	// Internal state ---------------------------------------------------------

		@Autowired
		protected BosseListService	listService;

		@Autowired
		protected BosseShowService	showService;
		
		@Autowired
		protected BosseUpdateService	updateService;
		
		@Autowired
		protected BosseCreateService	createService;
		
		@Autowired
		protected BosseDeleteService	deleteService;

		// Constructors -----------------------------------------------------------


		@PostConstruct
		protected void initialise() {
			super.addCommand("list", this.listService);
			super.addCommand("show", this.showService);
			super.addCommand("update", this.updateService);
			super.addCommand("create", this.createService);
			super.addCommand("delete", this.deleteService);
		}

}
