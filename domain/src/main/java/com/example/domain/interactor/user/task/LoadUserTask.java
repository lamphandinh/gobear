package com.example.domain.interactor.user.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;

public class LoadUserTask extends BaseTask<User> {

    private UserRepository userRepository;

    @Inject
    public LoadUserTask(UserRepository userRepo,
                        BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.userRepository = userRepo;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return null;
    }
}
