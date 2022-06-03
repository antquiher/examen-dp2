package acme.features.inventor.artifact;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.artifact.Artifact;
import acme.artifact.ArtifactType;
import acme.artifact.PartOf;
import acme.entities.bosse.Bosse;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;
import acme.systemSetting.SystemSetting;

@Repository
public interface InventorArtifactRepository extends AbstractRepository {

	@Query("select a from Artifact a where a.id = :id")
	Artifact findOneArtifactById(int id);

	@Query("select a from Artifact a where a.type = :arifact_type and a.inventor.id = :id")
	Collection<Artifact> findManyTool(ArtifactType arifact_type, int id);
	
	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorByInventorId(int id);
	
	@Query("select p from PartOf p where p.artifact.id = :id")
	Collection<PartOf> findAllPartoOfByArtifact(int id);
	
	@Query("select s from SystemSetting s")
	SystemSetting findSystemSetting();
	
	@Query("select a from Artifact a where a.code=:code")
	Artifact findByCode(String code);
	
	@Query("select c from Bosse c")
	Collection<Bosse> findAllChimpum();
}
