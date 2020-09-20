package com.placy.placycore.core.services;

import com.placy.placycore.core.model.OriginModel;
import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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

    public List<UserModel> getUserByOriginCodes(OriginModel originModel, List<String> originCodes) {
        return userRepository.findByOriginAndOriginCodeIn(originModel, originCodes);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Collection<UserModel> saveAllTransactional(List<UserModel> users) {
        return saveAll(users);
    }

    @Override
    public JpaRepository<UserModel, String> getRepository() {
        return userRepository;
    }
}
