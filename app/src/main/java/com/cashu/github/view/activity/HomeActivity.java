package com.cashu.github.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cashu.github.R;
import com.cashu.github.data.api.GithubApi;
import com.cashu.github.data.database.GithubDatabase;
import com.cashu.github.data.interactors.GithubDataInteractor;
import com.cashu.github.data.interactors.contract.GithubInteractor;
import com.cashu.github.data.model.GithubEntity;
import com.cashu.github.data.repository.LocalGithubRepository;
import com.cashu.github.data.repository.RemoteGithubRepository;
import com.cashu.github.data.repository.contract.BaseGithubRepository;
import com.cashu.github.presenter.GithubReposPresenter;
import com.cashu.github.presenter.views.HomeView;
import com.cashu.github.view.adapter.GithubAdapter;
import com.cashu.github.view.utils.EndlessRecyclerViewScrollListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @BindView(R.id.progress)
    ProgressBar mProgress;

    @BindView(R.id.recycler_repos)
    RecyclerView mReposRecycler;

    private GithubReposPresenter mPresenter;

    private static String OWNER_NAME = "JakeWharton";

    private static int PAGE_COUNT = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initPresenter();
        initRecycler();
    }

    private void initRecycler() {
        GithubAdapter githubAdapter = new GithubAdapter(new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,
                false);
        mReposRecycler.setLayoutManager(linearLayoutManager);
        mReposRecycler.setAdapter(githubAdapter);
        mReposRecycler.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager, 3) {
            @Override
            public void onLoadMore() {
                mPresenter.loadNextRepos(OWNER_NAME);
            }
        });
    }

    private void initPresenter() {
        //TODO: Use dagger to inject presenter and it's dependencies
        GithubDatabase githubDatabase = GithubDatabase.getDatabase(this.getApplicationContext());
        BaseGithubRepository<GithubEntity> localGithubRepository = new LocalGithubRepository(githubDatabase);
        BaseGithubRepository<GithubEntity> remoteGithubRepository = new RemoteGithubRepository(new GithubApi());
        GithubInteractor<GithubEntity> githubInteractor = new GithubDataInteractor<>(localGithubRepository,
                remoteGithubRepository, PAGE_COUNT);

        mPresenter = new GithubReposPresenter(githubInteractor, this);
        mPresenter.loadNextRepos(OWNER_NAME);
    }

    /**
     * */

    @Override
    public void showProgressBar() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showNewRepos(List<GithubEntity> repos) {
        if (mReposRecycler.getAdapter() != null)
            ((GithubAdapter) mReposRecycler.getAdapter()).addItems(repos);
    }

    @Override
    public void clearReposList() {
        if (mReposRecycler.getAdapter() != null)
            ((GithubAdapter) mReposRecycler.getAdapter()).clear();
    }

    @Override
    public void showErrorMessage(@GithubReposPresenter.ErrorTypes int errorType) {
        String message;
        switch (errorType) {
            case GithubReposPresenter.ErrorTypes.NETWORK_ERROR:
                message = getString(R.string.network_error);
                break;
            case GithubReposPresenter.ErrorTypes.NO_MORE_DATA_ERROR:
                message = getString(R.string.no_more_data_error);
                break;
            case GithubReposPresenter.ErrorTypes.UNKNOWN_ERROR:
                message = getString(R.string.unknown_error);
                break;
            default:
                message = getString(R.string.unknown_error);
        }
        Snackbar.make(findViewById(R.id.layout_parent), message, Snackbar.LENGTH_LONG)
                .show();
    }
}
