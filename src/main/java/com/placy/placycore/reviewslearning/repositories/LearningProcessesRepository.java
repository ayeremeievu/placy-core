package com.placy.placycore.reviewslearning.repositories;

import com.placy.placycore.reviewslearning.model.LearningProcessModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningProcessesRepository extends JpaRepository<LearningProcessModel, Integer> {
}
