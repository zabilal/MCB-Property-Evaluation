package gloomme.crater365.correspondence.repository;

import gloomme.crater365.correspondence.entity.Correspondence;
import gloomme.crater365.correspondence.entity.CorrespondenceLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorrespondenceLogsRepository extends JpaRepository<CorrespondenceLogs, Long> {
    List<CorrespondenceLogs> findAllByCorrespondence(Correspondence correspondence);
}
