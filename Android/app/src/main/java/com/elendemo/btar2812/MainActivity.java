package com.elendemo.btar2812;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager2 viewpager2;
    FragmentAdapter adapter;

    @Override
    public  void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = findViewById(R.id.tabs);
        viewpager2 = findViewById(R.id.view_pager2);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        viewpager2.setAdapter(adapter);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        viewpager2.setUserInputEnabled(false);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabs) {
                viewpager2.setCurrentItem(tabs.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tabs) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tabs) {
            }
        });

        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                tabs.selectTab(tabs.getTabAt(position));
            }
        });
    }
}
