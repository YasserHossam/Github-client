package com.cashu.github.data.database;

import androidx.room.*;
import com.cashu.github.data.model.GithubEntity;
import io.reactivex.Maybe;

import java.util.List;

@Dao
public interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(List<GithubEntity> repos);

    @Query("SELECT * from repositories where user_name LIKE :userName")
    Maybe<List<GithubEntity>> getUserRepos(String userName);

    @Query("DELETE FROM repositories")
    void deleteAll();
}