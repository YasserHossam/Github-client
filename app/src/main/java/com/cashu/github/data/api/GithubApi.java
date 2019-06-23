package com.cashu.github.data.api;

import com.google.gson.annotations.SerializedName;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public class GithubApi {

    interface Api {
        @GET("users/{user}/repos")
        Single<List<GithubResponse>> getNextRepos(@Path("user") String userName,
                                                  @Query("page") int pageNumber,
                                                  @Query("per_page") int count);
    }

    public Single<List<GithubResponse>> getReposForUser(String userName,
                                                        int pageNumber,
                                                        int count) {
        return RetrofitProvider.getRetrofit()
                .create(Api.class)
                .getNextRepos(userName, pageNumber, count);
    }

    public class GithubResponse {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("full_name")
        private String fullName;

        @SerializedName("private")
        private boolean _private;

        @SerializedName("owner")
        private Owner owner;

        @SerializedName("description")
        private String description;

        @SerializedName("stargazers_count")
        private int stargazersCount;

        @SerializedName("forks")
        private int forksCount;

        @SerializedName("open_issues")
        private int openIssuesCount;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getFullName() {
            return fullName;
        }

        public boolean isPrivate() {
            return _private;
        }

        public Owner getOwner() {
            return owner;
        }

        public String getDescription() {
            return description;
        }

        public int getStargazersCount() {
            return stargazersCount;
        }

        public int getForksCount() {
            return forksCount;
        }

        public int getOpenIssuesCount() {
            return openIssuesCount;
        }
    }

    public class Owner {
        @SerializedName("id")
        private int id;

        @SerializedName("avatar_url")
        private String avatarUrl;

        @SerializedName("url")
        private String url;

        @SerializedName("type")
        private String type;

        public int getId() {
            return id;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getUrl() {
            return url;
        }

        public String getType() {
            return type;
        }


    }
}
