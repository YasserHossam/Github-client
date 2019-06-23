package com.cashu.github.presenter.views;

import com.cashu.github.data.model.GithubEntity;
import com.cashu.github.presenter.GithubReposPresenter;

import java.util.List;

public interface HomeView {
    void showProgressBar();

    void hideProgressBar();

    void showNewRepos(List<GithubEntity> repos);

    void showErrorMessage(@GithubReposPresenter.ErrorTypes int errorType);

    void clearReposList();
}
