package com.example.domain.interactor.user.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.interactor.user.repo.UserRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetLastUserTokenTask extends BaseTask<String> {
    private UserRepository userRepositoty;

    @Inject
    public GetLastUserTokenTask(UserRepository userRepo,
                                BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.userRepositoty = userRepo;
    }

    @Override
    protected Observable<String> buildUseCaseObservable() {
        return userRepositoty.getLastUserToken();
    }
}
