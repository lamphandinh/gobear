package com.example.domain.interactor.user.repo;

import com.example.domain.dsextension.Pair;
import com.example.domain.model.User;

import rx.Observable;

public interface UserRepository {
    Observable<User> getLastUser();
    Observable<Pair<User , String>> getUserByName(String name);
    Observable<Boolean> saveUser(User user, String passHash);
}
