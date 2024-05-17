package com.anass.jplus.Fragments;

import static com.anass.jplus.MainActivity.clicksound;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anass.jplus.R;

public class Mangas extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mangas, container, false);




        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.GoBACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

}