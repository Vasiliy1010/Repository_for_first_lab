package ru.labs.hm1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.labs.hm1.model.Mission;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    Optional<Mission> findByMissionId(Integer id);
}