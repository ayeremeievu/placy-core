package com.placy.placycore.core.services;

import com.placy.placycore.core.model.CityModel;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class UserService extends AbstractModelService<UserModel, Integer> {
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

    public List<UserModel> getUsersByCity(CityModel cityModel) {
        return userRepository.findAllByCity(cityModel);
    }

    public Optional<UserModel> getUserByPk(int id) {
        return userRepository.findById(id);
    }

    public UserModel getUserByPkMandatory(int id) {
        Optional<UserModel> userModelOptional = getUserByPk(id);

        if(!userModelOptional.isPresent()) {
            throw new NoSuchElementException(
                    String.format("No user with id '%s' is found", id)
            );
        }

        return userModelOptional.get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Collection<UserModel> saveAllTransactional(List<UserModel> users) {
        return saveAll(users);
    }

    @Override
    public JpaRepository<UserModel, Integer> getRepository() {
        return userRepository;
    }
}
