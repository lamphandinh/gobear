package com.example.domain.interactor.user.task;

import com.example.domain.communication.error.UserNonRegisterException;
import com.example.domain.communication.error.WrongPassException;
import com.example.domain.dsextension.Pair;
import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class UserLoginUsecase extends BaseTask<User> {
    private LoadUserTask loadUserTask;
    private UserRepository userRepository;
    private String userName, userPassword;

    @Inject
    public UserLoginUsecase(LoadUserTask loadUserTask, UserRepository userRepository,
                            BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.loadUserTask = loadUserTask;
        this.userRepository = userRepository;
    }

    public void setUserData(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return Observable.just(true).map(new Func1<Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean validate) {
                if (userName == null || userName.equals("")) throw new IllegalArgumentException();
                if (userPassword == null || userPassword.equals(""))
                    throw new IllegalArgumentException();
                return true;
            }
        }).flatMap(new Func1<Boolean, Observable<Pair<User, String>>>() {
            @Override
            public Observable<Pair<User, String>> call(Boolean validate) {
                return userRepository.getUserByName(userName);
            }
        }).flatMap(new Func1<Pair<User, String>, Observable<User>>() {
            @Override
            public Observable<User> call(final Pair<User, String> userStringPair) {
                if (userStringPair == null || userStringPair.first == null) throw new UserNonRegisterException("");
                return userRepository.generateHashFromPassword(userPassword).map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String hash) {
                        return hash.equals(userStringPair.second);
                    }
                }).map(new Func1<Boolean, User>() {
                    @Override
                    public User call(Boolean matchHash) {
                        if (!matchHash) throw new WrongPassException("");
                        return userStringPair.first;
                    }
                });
            }
        });
    }
}
