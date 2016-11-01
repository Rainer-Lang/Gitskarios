package com.alorma.github.ui.fragment.commit;

import com.alorma.github.R;
import com.alorma.github.ui.fragment.base.BaseFragment;
import core.repositories.Commit;

public class CommitInfoFragment extends BaseFragment {

  public static CommitInfoFragment newInstance() {
    return new CommitInfoFragment();
  }

  @Override
  protected int getLightTheme() {
    return R.style.AppTheme_Repository;
  }

  @Override
  protected int getDarkTheme() {
    return R.style.AppTheme_Dark_Repository;
  }

  public void setCommit(Commit commit) {

  }

}
