package com.cashu.github.data.interactors;

import com.cashu.github.data.Exception.NoMoreDataException;
import com.cashu.github.data.Exception.OfflineNetworkException;
import com.cashu.github.data.interactors.contract.GithubInteractor;
import com.cashu.github.data.model.BaseRepositoryModel;
import com.cashu.github.data.model.GithubEntity;
import com.cashu.github.data.repository.contract.BaseGithubRepository;
import io.reactivex.Observable;
import retrofit2.HttpException;

import java.net.UnknownHostException;
import java.util.List;

/**
 * This class acts as a middle-man between presenters and repositories,
 */

public class GithubDataInteractor implements GithubInteractor<GithubEntity> {

    private BaseGithubRepository<GithubEntity> mLocalGithubRepository;

    private BaseGithubRepository<GithubEntity> mRemoteGithubRepository;

    private int mPageCount;

    private int mPageNumber;

    public GithubDataInteractor(BaseGithubRepository<GithubEntity> localGithubRepository,
                                BaseGithubRepository<GithubEntity> remoteGithubRepository,
                                int pageCount) {
        mLocalGithubRepository = localGithubRepository;
        mRemoteGithubRepository = remoteGithubRepository;
        mPageCount = pageCount;
        mPageNumber = 0;
    }

    /** Return a model of
     * 1- List of github repositories
     * 2- Boolean indicating whether an error occurred or not
     * 3- Exception of the returned Error
     **/
    /**
     * At first try to get the data from the remote repository, if succeeded,
     * Then map the data and save it in the database, If not succeeded,
     * Then try to get the data from local repository
     **/
    public Observable<BaseRepositoryModel<List<GithubEntity>>> getNextUserRepos(final String userName) {
        return mRemoteGithubRepository.getUserRepos(userName, mPageNumber, mPageCount)
                .map(entities -> new BaseRepositoryModel<>(entities, false,
                        null, BaseRepositoryModel.DataSource.API))
                .doOnNext(repositoryModel -> {
                    if (repositoryModel.getData().size() == 0)
                        throw new NoMoreDataException();
                    if (mPageNumber == 0)
                        mLocalGithubRepository.deleteAll();
                    mPageNumber++;
                    mLocalGithubRepository.add(repositoryModel.getData());
                }).onErrorResumeNext(throwable -> {
                    return mLocalGithubRepository
                            .getUserRepos(userName, mPageNumber, mPageCount)
                            .map(entities -> {
                                        Exception exception = (Exception) throwable;
                                        if (throwable instanceof HttpException || throwable instanceof UnknownHostException)
                                            exception = new OfflineNetworkException();
                                        return new BaseRepositoryModel<>(entities, true, exception,
                                                BaseRepositoryModel.DataSource.DATABASE);
                                    }
                            ).doOnNext(repositoryModel -> {
                                if (repositoryModel.getData().size() > 0) {
                                    mPageNumber = repositoryModel.getData().size() / mPageCount;
                                }
                            });
                });
    }
}
