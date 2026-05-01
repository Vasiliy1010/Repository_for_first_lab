package ru.labs.hm1.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "operation_timelines")
@Data
public class OperationTimeLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String timestamp = "Заглушка";

    @Column(columnDefinition = "TEXT")
    private String eventInfo;
}