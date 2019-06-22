package com.cashu.github.data.model.converter;

import com.cashu.github.data.api.GithubApi;
import com.cashu.github.data.model.GithubEntity;

import java.util.ArrayList;
import java.util.List;

public class GithubEntityConverter {

    public static GithubEntity convertFromGithubApiModel(GithubApi.GithubResponse githubApiModel) {
        String fullRepoName = githubApiModel.getFullName();
        String userName = fullRepoName.split("/")[0];
        return new GithubEntity(githubApiModel.getId(), githubApiModel.getName(),
                githubApiModel.isPrivate(), githubApiModel.getDescription(), githubApiModel.getStargazersCount(),
                githubApiModel.getForksCount(), githubApiModel.getOpenIssuesCount(),
                githubApiModel.getOwner().getId(), userName,
                githubApiModel.getOwner().getAvatarUrl(), githubApiModel.getOwner().getUrl());
    }

    public static List<GithubEntity> convertFromGithubApiModel(List<GithubApi.GithubResponse> githubApiModels) {
        ArrayList<GithubEntity> entities = new ArrayList<>();
        for (GithubApi.GithubResponse githubApiModel : githubApiModels) {
            entities.add(convertFromGithubApiModel(githubApiModel));
        }
        return entities;
    }
}
