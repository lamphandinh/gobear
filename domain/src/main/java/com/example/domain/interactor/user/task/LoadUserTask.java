package com.example.domain.interactor.user.task;

import com.example.domain.dsextension.Pair;
import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;

public class LoadUserTask extends BaseTask<Pair<User, String>> {
    private UserRepository userRepository;
    private String userName;
    @Inject
    public LoadUserTask(UserRepository userRepo,
                        BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.userRepository = userRepo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    protected Observable<Pair<User, String>> buildUseCaseObservable() {
        return userRepository.getUserByName(userName);
    }
}
