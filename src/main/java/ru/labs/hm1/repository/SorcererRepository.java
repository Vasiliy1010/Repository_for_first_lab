package ru.labs.hm1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.labs.hm1.model.Sorcerer;
import java.util.Optional;

@Repository
public interface SorcererRepository extends JpaRepository<Sorcerer, Integer> {
    Optional<Sorcerer> findById(Integer id);
}