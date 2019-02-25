package com.example.domain.interactor.user.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;

public class LastUserUsecase extends BaseTask<User> {
    private GetLastUserTask getLastUserTask;
    private String passHash;

    @Inject
    public LastUserUsecase(GetLastUserTask getLastUserTask,
                           BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.getLastUserTask = getLastUserTask;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return Observable.just(null);
    }

    public Observable<User> loadLastUserByPassHash(String passHash) {
        this.passHash = passHash;
        return buildUseCaseObservable();
    }
}
