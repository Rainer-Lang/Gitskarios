package com.alorma.github.ui.adapter.repos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EnhancedTextDelegate;

import com.alorma.github.GistsApplication;
import com.alorma.github.R;
import com.alorma.github.sdk.bean.dto.response.Repo;
import com.alorma.github.ui.events.ColorEvent;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

public class ReposAdapter extends ArrayAdapter<Repo> {

    private final LayoutInflater mInflater;
    private final Bus bus;

    private int textTitleColor;

    public ReposAdapter(Context context, List<Repo> repos) {
        super(context, 0, 0, repos);
        this.mInflater = LayoutInflater.from(context);
        this.textTitleColor = GistsApplication.AB_COLOR;

        bus = new Bus();
        bus.register(this);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.row_repo, viewGroup, false);
        ReposHolder reposHolder = new ReposHolder(v);

        Repo repo = getItem(position);

        reposHolder.textTitle.setText(repo.name);

        if (textTitleColor == 0) {
            textTitleColor = getContext().getResources().getColor(R.color.gray_github_dark);
        }
        reposHolder.textTitle.setTextColor(textTitleColor);

        IconDrawable typeDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_book);
        if (repo.fork) {
            typeDrawable = new IconDrawable(getContext(), Iconify.IconValue.fa_code_fork);
        }
        typeDrawable.colorRes(R.color.gray_github_medium);
        reposHolder.imageRepoType.setImageDrawable(typeDrawable);

        reposHolder.textDescription.setText(repo.description);

        String starText = getContext().getResources().getString(R.string.star_icon_text, repo.stargazers_count);
        reposHolder.textStarts.setText(starText);

        String forkText = getContext().getResources().getString(R.string.fork_icon_text, repo.forks_count);
        reposHolder.textForks.setText(forkText);

        reposHolder.textDescription.setText(repo.description);

        reposHolder.textLanguage.setText(repo.language);
        return v;
    }

    public void registerBus() {
        if (bus != null) {
            bus.register(this);
        }
    }

    public void unregisterBus() {
        if (bus != null) {
            bus.unregister(this);
        }
    }

    @Subscribe
    public void colorReceived(ColorEvent event) {
        this.textTitleColor = event.getRgb();
        notifyDataSetChanged();
    }
}