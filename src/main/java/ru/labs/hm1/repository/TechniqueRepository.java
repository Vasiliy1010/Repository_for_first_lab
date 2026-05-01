package ru.labs.hm1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.labs.hm1.model.Technique;
import java.util.List;

@Repository
public interface TechniqueRepository extends JpaRepository<Technique, Integer> {
    List<Technique> findBySorcererId(Integer sorcererId);
}