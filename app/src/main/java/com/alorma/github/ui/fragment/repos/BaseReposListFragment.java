package com.alorma.github.ui.fragment.repos;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.alorma.github.R;
import com.alorma.github.sdk.bean.dto.response.ListRepos;
import com.alorma.github.sdk.bean.dto.response.Repo;
import com.alorma.github.ui.activity.RepoDetailActivity;
import com.alorma.github.ui.adapter.repos.ReposAdapter;
import com.alorma.github.ui.fragment.base.PaginatedListFragment;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

/**
 * Created by Bernat on 17/07/2014.
 */
public abstract class BaseReposListFragment extends PaginatedListFragment<ListRepos> {

    protected ReposAdapter reposAdapter;

    protected void setUpList() {

        reposAdapter = new ReposAdapter(getActivity(), new ArrayList<Repo>());

        setListAdapter(reposAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (reposAdapter != null && reposAdapter.getCount() >= position) {
            Repo item = reposAdapter.getItem(position);
            if (item != null) {
                String repo = item.name;
                String owner = item.owner.login;
                Intent launcherActivity = RepoDetailActivity.createLauncherActivity(getActivity(), owner, repo, item.description);
                startActivity(launcherActivity);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (reposAdapter != null) {
            reposAdapter.registerBus();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (reposAdapter != null) {
            reposAdapter.unregisterBus();
        }
    }

    @Override
    protected void onQueryFail() {
        IconDrawable iconDrawable = new IconDrawable(getActivity(), Iconify.IconValue.fa_code);
        iconDrawable.colorRes(R.color.gray_github_medium);
        emptyIcon.setImageDrawable(iconDrawable);

        emptyText.setText(getNoDataText());

        emptyLy.setVisibility(View.VISIBLE);
    }

    protected abstract int getNoDataText();
}