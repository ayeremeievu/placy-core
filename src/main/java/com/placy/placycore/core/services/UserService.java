package com.placy.placycore.core.services;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService extends AbstractModelService<UserModel, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public JpaRepository<UserModel, String> getRepository() {
        return userRepository;
    }
}
