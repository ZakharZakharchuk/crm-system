package org.example.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "trainers")
@Data
@Accessors(chain = true)
@PrimaryKeyJoinColumn(name = "trainer_id")
public class Trainer extends User {

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
}
