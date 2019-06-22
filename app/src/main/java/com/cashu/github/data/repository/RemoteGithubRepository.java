package com.cashu.github.data.repository;

import com.cashu.github.data.api.GithubApi;
import com.cashu.github.data.model.GithubEntity;
import com.cashu.github.data.model.converter.GithubEntityConverter;
import com.cashu.github.data.repository.contract.BaseGithubRepository;
import io.reactivex.Observable;

import java.util.List;

public class RemoteGithubRepository implements BaseGithubRepository<GithubEntity> {

    private GithubApi githubApi;

    public RemoteGithubRepository(GithubApi githubApi) {
        this.githubApi = githubApi;
    }


    @Override
    public Observable<List<GithubEntity>> getUserRepos(String userName, int pageNumber,
                                                       int pageCount) {
        return githubApi.getReposForUser(userName, pageNumber, pageCount)
                .map(GithubEntityConverter::convertFromGithubApiModel)
                .toObservable();
    }

    @Override
    public void add(List<GithubEntity> entity) {
        //
    }

    @Override
    public void deleteAll() {
        //
    }
}
