package com.example.framework.service.user;

import com.example.domain.dsextension.Pair;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.domain.model.User;
import com.example.framework.cache.CacheFile;
import com.example.framework.db.GobearDatabase;
import com.example.framework.db.model.DBUser;
import com.example.framework.utils.SecurityHelper;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class UserRepositoryImpl implements UserRepository {
    private GobearDatabase gobearDatabase;
    private CacheFile cacheFile;
    @Inject
    public UserRepositoryImpl(GobearDatabase gobearDatabase, CacheFile cacheFile) {
       this.gobearDatabase = gobearDatabase;
       this.cacheFile = cacheFile;
    }

    @Override
    public Observable<String> generateHashFromPassword(String password) {
        return Observable.just(password).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return SecurityHelper.md5hex(s);
            }
        });
    }

    @Override
    public Observable<String> getLastUserToken() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return cacheFile.getLastUserToken();
            }
        });
    }

    @Override
    public Observable<Boolean> saveLastUserToken(final String passHash) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                cacheFile.setLastUserToken(passHash);
                return true;
            }
        });
    }

    @Override
    public Observable<Pair<User, String>> getUserByName(final String name) {
        return Observable.fromCallable(new Callable<Pair<User, String>>() {
            @Override
            public Pair<User, String> call() throws Exception {
                DBUser dbUser  = gobearDatabase.UserDao().findByName(name);
                User user = new User(dbUser.getName());
                return new Pair<>(user, dbUser.getHash());
            }
        });
    }

    @Override
    public Observable<User> getUserByToken(final String token) {
        return Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                DBUser dbUser  = gobearDatabase.UserDao().findByHash(token);
                return new User(dbUser.getName());
            }
        });
    }

    @Override
    public Observable<Boolean> saveUser(final User user, final String passHash) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                DBUser dbUser = new DBUser();
                dbUser.setName(user.getName());
                dbUser.setHash(passHash);
                gobearDatabase.UserDao().insert(dbUser);
                return true;
            }
        });
    }
}
