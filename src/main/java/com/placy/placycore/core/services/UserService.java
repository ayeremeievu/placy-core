package com.placy.placycore.core.services;

import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService extends AbstractModelService<UserModel, String> {
    @Autowired
    private UserRepository userRepository;

    public boolean existsUserByOrigin(OriginModel originModel, String originCode) {
        return getUserByOrigin(originModel, originCode).isPresent();
    }

    public Optional<UserModel> getUserByOrigin(OriginModel originModel, String originCode) {
        return userRepository.findFirstByOriginAndOriginCode(originModel, originCode);
    }

    @Override
    public JpaRepository<UserModel, String> getRepository() {
        return userRepository;
    }
}
