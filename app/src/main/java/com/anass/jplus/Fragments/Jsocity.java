package com.anass.jplus.Fragments;

import static com.anass.jplus.MainActivity.clicksound;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.anass.jplus.Activities.AddPost;
import com.anass.jplus.Adapters.PostAdapter;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.Responses.PostResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Jsocity extends Fragment {


    ProgressBar noresolttxt;
    RadioGroup grouiprado;

    RecyclerView post_rv;
    RelativeLayout addpost;

    getAlltvsViewModel getAlltvsViewModel;


    int curentpage = 1;
    int totalpage = 1;

    List<PostModel> postModels   = new ArrayList<>( );

    PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v =  inflater.inflate(R.layout.fragment_jsocity, container, false);
        initi(v);


        if (getActivity() != null){

            addpost.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    clicksound(getActivity());
                    startActivity(new Intent( getActivity(), AddPost.class ));

                }
            });
        }

        getPosts();

      /*  grouiprado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.manxorat_select:
                        noresolttxt.setVisibility(View.VISIBLE);
                        postModels.clear();
                        postAdapter.notifyDataSetChanged();

                        getPosts();

                        break;
                    case R.id.pupaler_select:
                        noresolttxt.setVisibility(View.VISIBLE);

                        postModels.clear();
                        postAdapter.notifyDataSetChanged();

                        getmostliked();
                        break;
                    case R.id.trend_select:
                        noresolttxt.setVisibility(View.VISIBLE);
                        postModels.clear();
                        postAdapter.notifyDataSetChanged();

                        gettrend();
                        break;
                    case R.id.random_select:
                        noresolttxt.setVisibility(View.VISIBLE);
                        postModels.clear();
                        postAdapter.notifyDataSetChanged();
                        getRandom();
                        break;

                }
            }
        });*/



    return v;
    }

    private void getRandom() {
        getAlltvsViewModel.random(curentpage).observe(getActivity( ), new Observer<PostResponse>( ) {
            @Override
            public void onChanged(PostResponse postResponse) {

                if (postResponse != null){
                    totalpage = postResponse.getTotal();
                    if (postResponse.getGetposts() != null){
                        for (PostModel post:postResponse.getGetposts()) {
                            Log.d("TAG", "onChanged: "+post.getId());
                        }
                        noresolttxt.setVisibility(View.GONE);
                        int oldCount = postModels.size();

                        postModels.addAll(postResponse.getGetposts());
                        postAdapter.notifyItemRangeInserted(oldCount,postModels.size());

                    }
                }


            }
        });


    }

    private void gettrend() {

        getAlltvsViewModel.trendpost("ma",curentpage).observe(getActivity( ), new Observer<PostResponse>( ) {
            @Override
            public void onChanged(PostResponse postResponse) {

                if (postResponse != null){
                    totalpage = postResponse.getTotal();
                    if (postResponse.getGetposts() != null){
                        for (PostModel post:postResponse.getGetposts()) {
                            Log.d("TAG", "onChanged: "+post.getId());
                        }
                        noresolttxt.setVisibility(View.GONE);
                        int oldCount = postModels.size();

                        postModels.addAll(postResponse.getGetposts());
                        postAdapter.notifyItemRangeInserted(oldCount,postModels.size());

                    }
                }


            }
        });

    }

    private void getmostliked() {

        getAlltvsViewModel.mostliked(curentpage).observe(getActivity( ), new Observer<PostResponse>( ) {
            @Override
            public void onChanged(PostResponse postResponse) {

                if (postResponse != null){
                    totalpage = postResponse.getTotal();
                    if (postResponse.getGetposts() != null){
                        for (PostModel post:postResponse.getGetposts()) {
                            Log.d("TAG", "onChanged: "+post.getId());
                        }
                        noresolttxt.setVisibility(View.GONE);
                        int oldCount = postModels.size();

                        postModels.addAll(postResponse.getGetposts());
                        postAdapter.notifyItemRangeInserted(oldCount,postModels.size());

                    }
                }


            }
        });

    }

    private void getPosts() {



        getAlltvsViewModel.posts(curentpage).observe(getActivity( ), new Observer<PostResponse>( ) {
            @Override
            public void onChanged(PostResponse postResponse) {

                if (postResponse != null){
                    totalpage = postResponse.getTotal();
                    if (postResponse.getGetposts() != null){
                        noresolttxt.setVisibility(View.GONE);
                        postAdapter = new PostAdapter(getActivity(),postResponse.getGetposts());
                        post_rv.setAdapter(postAdapter);

                    }
                }


            }
        });


    }



    private void initi(View v) {
        getAlltvsViewModel = new ViewModelProvider(getActivity()).get(getAlltvsViewModel.class);


        noresolttxt = v.findViewById(R.id.noresolttxt);
        grouiprado = v.findViewById(R.id.grouiprado);
        post_rv = v.findViewById(R.id.post_rv);
        addpost = v.findViewById(R.id.addpost);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        post_rv.setLayoutManager(layoutManager);
        post_rv.setNestedScrollingEnabled(false);



        post_rv.addOnScrollListener(new RecyclerView.OnScrollListener( ) {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Get the first and last visible item positions
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
                    postAdapter.getItem(i);
                }

            }
        });

        getPosts();


    }




    public static class OverlapFollowesAdapter extends RecyclerView.Adapter<OverlapFollowesAdapter.myviewholder>{

        Context context;
        List<UserModel> userModels;

        int limit = 3;

        public OverlapFollowesAdapter(Context context,List<UserModel> userModels) {
            this.context = context;
            this.userModels = userModels;
        }



        @NonNull
        @Override
        public OverlapFollowesAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.overlap_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull OverlapFollowesAdapter.myviewholder holder, int position) {


            if (context != null){
                Glide.with(context).load(userModels.get(position).getProfil())
                        .into(holder.show_profile);
            }


        }

        @Override
        public int getItemCount() {
              if(userModels.size() > limit){
                return limit ;
            }
            else
            {
                return userModels.size() ;
            }
        }

        public class myviewholder extends RecyclerView.ViewHolder {

            CircleImageView show_profile;
            public myviewholder(@NonNull View itemView) {
                super(itemView);

                show_profile = itemView.findViewById(R.id.show_profile);
            }
        }
    }



}