package acme.features.administrator.AdminDashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.artifact.Artifact;
import acme.artifact.ArtifactType;
import acme.datatypes.StatusType;
import acme.entities.bosse.Bosse;
import acme.entities.patronage.Patronage;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdminDashboardRepository extends AbstractRepository {

	@Query("select a from Artifact a where a.type = :artifact_type")
	Collection<Artifact> findArtifact(ArtifactType artifact_type);

	@Query("select a from Artifact a where  a.technology=:technology and a.type = :artifact_type")
	Collection<Artifact> findArtifactTechnologyCurrency(String technology, ArtifactType artifact_type);

	@Query("select p from Patronage p where  p.status=:status")
	Collection<Patronage> findPatronage(StatusType status);
	
	@Query("select c from Bosse c")
	Collection<Bosse> findAllChimpums();
}
