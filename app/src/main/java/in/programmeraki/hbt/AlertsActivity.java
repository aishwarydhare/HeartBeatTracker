package in.programmeraki.hbt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import in.programmeraki.hbt.adapter.ViewPagerAdapter;
import in.programmeraki.hbt.fragment.CommonAlertsFragment;

public class AlertsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout_textview,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tealPrimaryDark));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.tealSecondaryMid),
                ContextCompat.getColor(this, R.color.tealSecondaryMid));

        findViewById(R.id.back_iv).setOnClickListener(view -> {
            finish();
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CommonAlertsFragment(), "Past");
        adapter.addFragment(new CommonAlertsFragment(), "Upcoming");
        viewPager.setAdapter(adapter);
    }

}
