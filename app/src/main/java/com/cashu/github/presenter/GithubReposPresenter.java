package com.cashu.github.presenter;

import androidx.annotation.IntDef;
import com.cashu.github.data.exception.NoMoreDataException;
import com.cashu.github.data.exception.OfflineNetworkException;
import com.cashu.github.data.interactors.contract.GithubInteractor;
import com.cashu.github.data.model.BaseRepositoryModel;
import com.cashu.github.data.model.GithubEntity;
import com.cashu.github.presenter.views.HomeView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class GithubReposPresenter extends BasePresenter {

    private HomeView mHomeView;

    private GithubInteractor<GithubEntity> mGithubInteractor;

    private boolean dataLimitReached = false;

    public GithubReposPresenter(GithubInteractor<GithubEntity> githubInteractor,
                                HomeView homeView) {
        mGithubInteractor = githubInteractor;
        mHomeView = homeView;
    }

    public void loadNextRepos(String userName) {
        if (dataLimitReached)
            return;
        mHomeView.showProgressBar();
        mGithubInteractor.getNextUserRepos(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new BaseObserver<BaseRepositoryModel<List<GithubEntity>>>() {
                    @Override
                    public void onNext(BaseRepositoryModel<List<GithubEntity>> repositoryModel) {
                        mHomeView.hideProgressBar();

                        // if the data was from api then there will be paging
                        // else if it was from database, all of the items are being returned and there is no paging.
                        if (repositoryModel.getDataSource() == BaseRepositoryModel.DataSource.DATABASE)
                            mHomeView.clearReposList();

                        mHomeView.showNewRepos(repositoryModel.getData());

                        // Handle known errors
                        if (repositoryModel.isErrored() && repositoryModel.getError() != null) {
                            if (repositoryModel.getError() instanceof OfflineNetworkException)
                                mHomeView.showErrorMessage(ErrorTypes.NETWORK_ERROR);
                            else if (repositoryModel.getError() instanceof NoMoreDataException) {
                                mHomeView.showErrorMessage(ErrorTypes.NO_MORE_DATA_ERROR);
                                dataLimitReached = true;
                            } else
                                mHomeView.showErrorMessage(ErrorTypes.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.hideProgressBar();
                        mHomeView.showErrorMessage(ErrorTypes.UNKNOWN_ERROR);
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
