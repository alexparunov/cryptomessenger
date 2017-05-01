package alexparunov.stegomessenger.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Alexander Parunov on 5/2/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

  private List<Fragment> fragments;

  public ViewPagerAdapter(List<Fragment> fragments, FragmentManager fm) {
    super(fm);
    this.fragments = fragments;
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return fragments.size();
  }
}
