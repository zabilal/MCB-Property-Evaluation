package gloomme.crater365.correspondence.repository;

import gloomme.crater365.correspondence.entity.Correspondence;
import gloomme.crater365.correspondence.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findAllByCorrespondence(Correspondence correspondence);
}
