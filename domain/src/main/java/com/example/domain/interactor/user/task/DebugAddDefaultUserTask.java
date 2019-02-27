package com.example.domain.interactor.user.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class DebugAddDefaultUserTask extends BaseTask<Boolean> {
    private UserRepository userRepository;
    private String defaultUserName;
    private String defaultPass;

    @Inject
    public DebugAddDefaultUserTask(UserRepository userRepository,
                            BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setupDefaultData(String userName, String pass) {
        this.defaultUserName = userName;
        this.defaultPass = pass;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable() {
        return userRepository.generateHashFromPassword(defaultPass).flatMap(new Func1<String, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(String passHash) {
                User defaultUser = new User(defaultUserName);
                return userRepository.saveUser(defaultUser, passHash);
            }
        });
    }
}
