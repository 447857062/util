package kelijun.com.utilprojection;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {
    private CommonTabLayout mTablayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;

    private NoScrollViewPager mViewPager;

    private AppBarLayout mAppBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mTablayout = (CommonTabLayout) findViewById(R.id.uc_tablayout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.uc_viewpager);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        initTab();

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());

                    if (percent == 0) {
                        groupChange(1f, 1);
                    } else if (percent == 1) {

                        groupChange(1f, 2);
                    } else {

                        groupChange(percent, 0);
                    }


            }
        });
        AppBarLayoutOverScrollViewBehavior myAppBarLayoutBehavoir = (AppBarLayoutOverScrollViewBehavior)
                ((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();
        myAppBarLayoutBehavoir.setOnProgressChangeListener(new AppBarLayoutOverScrollViewBehavior.onProgressChangeListener() {
            @Override
            public void onProgressChange(float progress, boolean isRelease) {



            }
        });
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * @param alpha
     * @param state 0-正在变化 1展开 2 关闭
     */
    public void groupChange(float alpha, int state) {


        switch (state) {
            case 1://完全展开 显示白色

                mViewPager.setNoScroll(false);
                break;
            case 2://完全关闭 显示黑色

                mViewPager.setNoScroll(false);
                break;
            case 0://介于两种临界值之间 显示黑色

                mViewPager.setNoScroll(true);
                break;
        }
    }
    /**
     * 初始化tab
     */
    private void initTab() {
        fragments = getFragments();
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, getNames());
        mTablayout.setTabData(mTabEntities);
        mViewPager.setAdapter(myFragmentPagerAdapter);
    }
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ItemFragment1());
        fragments.add(new ItemFragment1());
        fragments.add(new ItemFragment1());
        fragments.add(new ItemFragment1());
        return fragments;
    }
    /**
     * 假数据
     *
     * @return
     */
    public String[] getNames() {
        String[] mNames = new String[]{"Weather", "Moon", "Like", "Fans"};
        for (String str : mNames) {
            mTabEntities.add(new TabEntity(String.valueOf(new Random().nextInt(200)), str));
        }

        return mNames;
    }
}
