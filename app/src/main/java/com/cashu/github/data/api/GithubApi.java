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

        @SerializedName("node_id")
        private String nodeId;

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

        public String getNodeId() {
            return nodeId;
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

        @SerializedName("login")
        private String login;

        @SerializedName("id")
        private int id;

        @SerializedName("node_id")
        private String nodeId;

        @SerializedName("avatar_url")
        private String avatarUrl;

        @SerializedName("gravatar_id")
        private String gravatarId;

        @SerializedName("url")
        private String url;

        @SerializedName("html_url")
        private String htmlUrl;

        @SerializedName("followers_url")
        private String followersUrl;

        @SerializedName("following_url")
        private String followingUrl;

        @SerializedName("gists_url")
        private String gistsUrl;

        @SerializedName("starred_url")
        private String starredUrl;

        @SerializedName("subscriptions_url")
        private String subscriptionsUrl;

        @SerializedName("organizations_url")
        private String organizationsUrl;

        @SerializedName("repos_url")
        private String reposUrl;

        @SerializedName("events_url")
        private String eventsUrl;

        @SerializedName("received_events_url")
        private String receivedEventsUrl;

        @SerializedName("type")
        private String type;

        @SerializedName("site_admin")
        private boolean siteAdmin;

        public String getLogin() {
            return login;
        }

        public int getId() {
            return id;
        }

        public String getNodeId() {
            return nodeId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getGravatarId() {
            return gravatarId;
        }

        public String getUrl() {
            return url;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public String getFollowersUrl() {
            return followersUrl;
        }

        public String getGistsUrl() {
            return gistsUrl;
        }

        public String getFollowingUrl() {
            return followingUrl;
        }

        public String getStarredUrl() {
            return starredUrl;
        }

        public String getSubscriptionsUrl() {
            return subscriptionsUrl;
        }

        public String getOrganizationsUrl() {
            return organizationsUrl;
        }

        public String getReposUrl() {
            return reposUrl;
        }

        public String getEventsUrl() {
            return eventsUrl;
        }

        public String getReceivedEventsUrl() {
            return receivedEventsUrl;
        }

        public String getType() {
            return type;
        }

        public boolean isSiteAdmin() {
            return siteAdmin;
        }


    }
}
