package gloomme.crater365.correspondence.repository;

import gloomme.crater365.correspondence.entity.Correspondence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorrespondenceRepository extends JpaRepository<Correspondence, Long> {
    Optional<Correspondence> findCorrespondenceByCraterId(String craterId);

    Optional<Correspondence> findCorrespondenceByTitle(String title);

    Page<Correspondence> findByOrganizationCraterId(String organizationCraterId, Pageable pageable);

    Optional<Correspondence> findByOrganizationCraterIdAndCraterId(String organizationCraterId, String craterId);
    Optional<Correspondence> findByOrganizationCraterIdAndTitle(String title, String craterId);
}
