package com.example.domain.interactor.user.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.domain.model.User;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class LastUserUsecase extends BaseTask<User> {
    private GetLastUserTokenTask getLastUserTokenTask;
    private UserRepository userRepository;

    @Inject
    public LastUserUsecase(GetLastUserTokenTask getLastUserTokenTask, UserRepository userRepository,
                           BatchExecutor batchExecutor, PostExecutionThread postExecutionThread) {
        super(batchExecutor, postExecutionThread);
        this.getLastUserTokenTask = getLastUserTokenTask;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return getLastUserTokenTask.buildUseCaseObservable().flatMap(new Func1<String, Observable<User>>() {
            @Override
            public Observable<User> call(String lastUserToken) {
                if (lastUserToken == null || lastUserToken.equals("")) return Observable.just(null);
                return userRepository.getUserByToken(lastUserToken);
            }
        });
    }
}
