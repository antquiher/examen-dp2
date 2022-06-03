package acme.features.inventor.bosse;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.artifact.Artifact;
import acme.artifact.ArtifactType;
import acme.entities.bosse.Bosse;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BosseRepository extends AbstractRepository{
	
	@Query("select c from Bosse c where c.id = :id")
	Bosse findOneBosseById(int id);

	@Query("select c from Bosse c")
	Collection<Bosse> findManyBosse();
	
	@Query("select a from Artifact a LEFT JOIN Bosse c ON c.artefact=a WHERE c IS NULL and a.type=:type")
	List<Artifact> findArtifactList(ArtifactType type);
	
	@Query("select a from Artifact a where a.id = :id")
	Artifact findArtifactById(int id);
	
	@Query("select c from Bosse c where c.code = :code")
	Bosse findAnyBosseByCode(String code);
	
}
