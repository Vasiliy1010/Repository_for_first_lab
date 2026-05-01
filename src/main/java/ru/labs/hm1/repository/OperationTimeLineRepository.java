package ru.labs.hm1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.labs.hm1.model.OperationTimeLine;

@Repository
public interface OperationTimeLineRepository extends JpaRepository<OperationTimeLine, Integer> {
}