package com.cashu.github.presenter;

import androidx.annotation.IntDef;
import com.cashu.github.data.exception.NoMoreDataException;
import com.cashu.github.data.exception.OfflineNetworkException;
import com.cashu.github.data.interactors.GithubDataInteractor;
import com.cashu.github.data.model.BaseRepositoryModel;
import com.cashu.github.data.model.GithubEntity;
import com.cashu.github.presenter.views.GithubReposView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class GithubReposPresenter extends BasePresenter {

    private GithubReposView mGithubReposView;

    private GithubDataInteractor mGithubRepository;

    private boolean dataLimitReached = false;

    public GithubReposPresenter(GithubDataInteractor githubRepository,
                                GithubReposView githubReposView) {
        mGithubRepository = githubRepository;
        mGithubReposView = githubReposView;
    }

    public void loadNextRepos(String userName) {
        if (dataLimitReached)
            return;
        mGithubReposView.showProgressBar();
        mGithubRepository.getNextUserRepos(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new BaseObserver<BaseRepositoryModel<List<GithubEntity>>>() {
                    @Override
                    public void onNext(BaseRepositoryModel<List<GithubEntity>> repositoryModel) {
                        mGithubReposView.hideProgressBar();

                        // if the data was from api then there will be paging
                        // else if it was from database, all of the items are being returned and there is no paging.
                        if (repositoryModel.getDataSource() == BaseRepositoryModel.DataSource.DATABASE)
                            mGithubReposView.clearReposList();

                        mGithubReposView.showNewRepos(repositoryModel.getData());

                        // Handle known errors
                        if (repositoryModel.isErrored() && repositoryModel.getError() != null) {
                            if (repositoryModel.getError() instanceof OfflineNetworkException)
                                mGithubReposView.showErrorMessage(ErrorTypes.NETWORK_ERROR);
                            else if (repositoryModel.getError() instanceof NoMoreDataException) {
                                mGithubReposView.showErrorMessage(ErrorTypes.NO_MORE_DATA_ERROR);
                                dataLimitReached = true;
                            } else
                                mGithubReposView.showErrorMessage(ErrorTypes.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mGithubReposView.hideProgressBar();
                        mGithubReposView.showErrorMessage(ErrorTypes.UNKNOWN_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @IntDef({ErrorTypes.UNKNOWN_ERROR, ErrorTypes.NETWORK_ERROR, ErrorTypes.NO_MORE_DATA_ERROR})
    public @interface ErrorTypes {
        int UNKNOWN_ERROR = 0;
        int NETWORK_ERROR = 1;
        int NO_MORE_DATA_ERROR = 2;
    }
}
