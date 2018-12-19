package com.tech.cny.module_autoscroll;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

/**
 * Created by Hua on 2018-12-19.
 * Power By ZHLian
 */

public class MainFragment extends Fragment{
    private static final String TAG = "MainFragment";
    private ViewPager mViewPager;
    private FragmentAdapter adapter;
    private Fragment mHelpFragment;
    private ConnectFragment mConnectFragment;
    private RadioGroup mRadioGroup;

    private boolean isChildInit;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"onAttach");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mViewPager = view.findViewById(R.id.viewpager);
        mRadioGroup = view.findViewById(R.id.radiogroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radiobtn_1:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.radiobtn_2:
                        mViewPager.setCurrentItem(1);
                        break;
                }
            }
        });
        if (null != savedInstanceState)
        isChildInit = savedInstanceState.getBoolean("isInited");
        if (!isChildInit){
            mHelpFragment = new HelpFragment();
            mConnectFragment = new ConnectFragment();
            adapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
            mViewPager.setAdapter(adapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position==0){
                        mRadioGroup.check(R.id.radiobtn_1);
                    }else {
                        mRadioGroup.check(R.id.radiobtn_2);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            Log.e(TAG,"fragment inited success----");
            isChildInit = true;
        }else {
            Log.e(TAG,"fragment already inited------");
//            isChildInit = true;
        }

        mViewPager.setCurrentItem(0);
        mRadioGroup.check(R.id.radiobtn_1);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }


    @Override
    public void onResume() {

        Log.e(TAG,"onResume"+this);
        super.onResume();

    }



    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG,"onDestoryView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestory");
    }


    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return mConnectFragment;
            }
            return mHelpFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("isInited",isChildInit);
        super.onSaveInstanceState(outState);
        Log.e(TAG,"onSaveInstanceState"+isChildInit);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.e(TAG,"onViewStateRestored");

    }
}
