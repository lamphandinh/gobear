package com.example.domain.interactor.user.repo;

import com.example.domain.dsextension.Pair;
import com.example.domain.model.User;

import rx.Observable;

public interface UserRepository {
    Observable<String> generateHashFromPassword(String password);
    Observable<String> getLastUserToken();
    Observable<Pair<User, String>> getUserByName(String name);
    Observable<User> getUserByToken(String token);
    Observable<Boolean> saveUser(User user, String passHash);
}
