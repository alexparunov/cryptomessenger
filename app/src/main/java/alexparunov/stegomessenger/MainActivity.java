package alexparunov.stegomessenger;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import alexparunov.stegomessenger.activities_fragments.decrypt.DecryptFragment;
import alexparunov.stegomessenger.activities_fragments.encrypt.EncryptFragment;
import alexparunov.stegomessenger.adapters.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.tablayout)
  TabLayout tabLayout;
  @BindView(R.id.viewpager)
  ViewPager viewPager;

  private ViewPagerAdapter viewPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    initUI();
  }

  private void initUI() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(false);
      actionBar.setTitle("Crypto Messenger");
    }

    List<Fragment> tabs = new ArrayList<>();
    tabs.add(new EncryptFragment());
    tabs.add(new DecryptFragment());

    viewPagerAdapter = new ViewPagerAdapter(tabs, getSupportFragmentManager());
    viewPager.setAdapter(viewPagerAdapter);
    setSupportActionBar(toolbar);

    tabLayout.addTab(tabLayout.newTab().setText("Encryption"));
    tabLayout.addTab(tabLayout.newTab().setText("Decryption"));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

}
