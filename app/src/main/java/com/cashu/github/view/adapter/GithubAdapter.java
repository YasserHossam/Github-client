package com.cashu.github.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.cashu.github.R;
import com.cashu.github.data.model.GithubEntity;

import java.util.List;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.GithubRepoViewHolder> {


    private List<GithubEntity> mGithubEntities;

    public GithubAdapter(List<GithubEntity> mGithubEntities) {
        this.mGithubEntities = mGithubEntities;
    }

    @NonNull
    @Override
    public GithubRepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View repoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new GithubRepoViewHolder(repoView);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubRepoViewHolder holder, int position) {
        holder.bind(mGithubEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return mGithubEntities == null ? 0 : mGithubEntities.size();
    }

    public void addItems(List<GithubEntity> entities) {
        mGithubEntities.addAll(entities);
        notifyDataSetChanged();
    }

    public void clear() {
        mGithubEntities.clear();
        notifyDataSetChanged();
    }

    class GithubRepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView nameTextView;

        @BindView(R.id.tv_owner_name)
        TextView ownerNameTextView;

        @BindView(R.id.tv_privacy)
        TextView privacyTextView;

        @BindView(R.id.tv_description)
        TextView descriptionTextView;

        @BindView(R.id.tv_stars_count)
        TextView starsCountTextView;

        @BindView(R.id.tv_forks_count)
        TextView forksCountTextView;

        @BindView(R.id.iv_profile_image)
        ImageView profileImgeView;


        GithubRepoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(GithubEntity entity) {
            nameTextView.setText(entity.getName());
            ownerNameTextView.setText(entity.getUserName());
            if (entity.is_private())
                privacyTextView.setText(itemView.getContext().getString(R.string.private_repo));
            else
                privacyTextView.setText(itemView.getContext().getString(R.string.public_repo));
            descriptionTextView.setText(entity.getDescription());
            starsCountTextView.setText(String.valueOf(entity.getStarsCount()));
            forksCountTextView.setText(String.valueOf(entity.getForksCount()));

            Glide.with(itemView)
                    .load(entity.getOwnerAvatarUrl())
                    .into(profileImgeView);
        }
    }
}
