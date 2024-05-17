package com.anass.jplus.Activities.MyList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.anass.jplus.Fragments.movies;
import com.anass.jplus.Fragments.series;
import com.anass.jplus.MainActivity;
import com.google.android.material.tabs.TabLayout;

import com.anass.jplus.R;


public class theList extends Fragment {


    TabLayout tablay;
    ViewPager2 viewpager2;

    MyViewSearchAdapter myViewPagerSearchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_the_list, container, false);
        ((MainActivity)getActivity()).showStatusBar();


        initialviews(view);




        return view;
    }

    private void initialviews(View v) {

        tablay = v.findViewById(R.id.tablay);
        viewpager2 = v.findViewById(R.id.viewpager2);

        myViewPagerSearchAdapter = new  MyViewSearchAdapter(getActivity());


        viewpager2.setAdapter(myViewPagerSearchAdapter);
        viewpager2.setOffscreenPageLimit(2);

        tablay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tablay.getTabAt(position).select();
            }
        });



    }

    public class MyViewSearchAdapter extends FragmentStateAdapter {

        public String searchqq;

        public void setSearchqq(String searchqq) {
            this.searchqq = searchqq;
        }

        public MyViewSearchAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new series();

                default:
                    return new movies();
            }

        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}

