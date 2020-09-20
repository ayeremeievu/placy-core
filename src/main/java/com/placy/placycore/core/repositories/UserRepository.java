package com.placy.placycore.core.repositories;

import com.placy.placycore.core.model.DivisionModel;
import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findFirstByOriginAndOriginCode(OriginModel originModel, String originCode);

    List<UserModel> findByOriginAndOriginCodeIn(OriginModel originModel, List<String> originCodes);
}
