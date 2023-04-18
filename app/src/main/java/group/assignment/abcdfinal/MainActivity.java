package group.assignment.abcdfinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import group.assignment.abcdfinal.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addTabs();
    }

    private void init(){
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void addTabs() {

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_add));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_like));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_like_fill));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_homefill);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_homefill);
                        break;

                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
                        break;

                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_add);
                        break;

                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_like_fill);
                        break;

                    case 4:
                        tabLayout.getTabAt(4).setIcon(android.R.drawable.ic_menu_help);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                        break;

                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
                        break;

                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_add);
                        break;

                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_like);
                        break;

                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_like_fill);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_homefill);
                        break;

                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
                        break;

                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_add);
                        break;

                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_like_fill);
                        break;

                    case 4:
                        tabLayout.getTabAt(4).setIcon(android.R.drawable.ic_menu_help);
                        break;
                }
            }
        });
    }

}