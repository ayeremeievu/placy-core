package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
}
