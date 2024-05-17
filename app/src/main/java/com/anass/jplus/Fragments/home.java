package com.anass.jplus.Fragments;

import static android.content.Context.MODE_PRIVATE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;
import static com.anass.jplus.Constant.AppConfig.adType;
import static com.anass.jplus.MainActivity.clicksound;
import static com.anass.jplus.SplashScreen.headers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.anass.jplus.Activities.Notification;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Adapters.AddToFavAdapter;
import com.anass.jplus.Adapters.ChapterAdapter;
import com.anass.jplus.Adapters.EpisodeAdapter;
import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.MainActivity;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.addtofavModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.Responses.ChaptersResponse;
import com.anass.jplus.backend.Responses.EpisodesResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
;
import com.nvanbenschoten.motion.ParallaxImageView;
import com.startapp.sdk.ads.banner.Banner;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class home extends Fragment {


    ParallaxImageView image_thumbnail;

    RelativeLayout watchepenoew;

    //images
    ImageView search_btn,show_profile,cartoon_logo;
    TextView geners_cartoon;

    //Recyclers
    RecyclerView toptvtoday_rv,pined_rv,topmoviemonth_rv,lastepeadded_rv,lastchapters_rv,comicstopvisit_rv
            ,mosttvandmovieaddedtofavourit_rv;
    LinearLayout motabata;
    RecyclerView newtvadded_rv;

    ExecutorService executorService;

    getAlltvsViewModel movieListViewModel;

    List<CartoonModel> alltvs = new ArrayList<>(  );

    AuthManager authManager;
    RelativeLayout adViewLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity)getActivity()).hideStatusBar();
        authManager = new AuthManager(getContext().getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));


        initial(view);

        if (authManager.getUserInfo().getId() > 0){
            if (authManager.getUserInfo().getNoads() == 0){
                loadBanner();
            }
        }else{
            loadBanner();
        }

       GetSlider(view);
       Getpin(view);
       Getpupplartvsforthisday(view);
       GetpupplarMoviesforthisMounth(view);
       Getlastepeadded(view);
      // Getlastchapters(view);
       Getnewtvadded(view);
       Getcomicstopvisit(view);
       Getmostcartoonaddedtofav(view);




        if (getActivity() != null) {
            search_btn.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    clicksound(getActivity());
                }
            });
            Top3btngo(view);
            show_profile.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    clicksound(getActivity());
                    if (authManager.getUserInfo().getId() > 0){
                        startActivity(new Intent( getActivity(), Notification.class));
                    }else {
                        Toast.makeText(getContext(), "سجل الدخول أولا", Toast.LENGTH_SHORT).show( );
                    }
                }
            });




        }

        return  view;
    }

    private void loadBanner() {

        Context context = getContext();

            // Define StartApp Banner
            Banner startAppBanner = new Banner(context);
            RelativeLayout.LayoutParams bannerParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            // Add to main Layout
            adViewLayout.addView(startAppBanner, bannerParameters);


    }

    private void Getmostcartoonaddedtofav(View view) {

        mosttvandmovieaddedtofavourit_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        movieListViewModel.mosttvandmovieaddedtofavourit().observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    if (cartoonResponse.getTvs() != null){
                        view.findViewById(R.id.cartoonaddedtofavourit).setVisibility(View.VISIBLE);
                        mosttvandmovieaddedtofavourit_rv.setAdapter(new SeriesAdapter(getActivity(),cartoonResponse.getTvs(),3));
                    }
                }


            }
        });
    }

    private void Getcomicstopvisit(View view) {

        comicstopvisit_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        movieListViewModel.topcomics().observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    if (cartoonResponse.getTvs() != null){
                        if (cartoonResponse.getTvs().size() > 0){
                            view.findViewById(R.id.aktarcomicsbiews).setVisibility(View.VISIBLE);
                            comicstopvisit_rv.setAdapter(new SeriesAdapter(getActivity(),cartoonResponse.getTvs(),3));
                        }

                    }
                }


            }
        });
    }

    private void Getnewtvadded(View view) {

        newtvadded_rv.setLayoutManager(new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false));

        movieListViewModel.newrelease().observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    if (cartoonResponse.getTvs() != null){
                        view.findViewById(R.id.mosalastjadida).setVisibility(View.VISIBLE);
                        newtvadded_rv.setAdapter(new SeriesAdapter(getActivity(),cartoonResponse.getTvs(),1));
                    }
                }


            }
        });






    }

    private void Getlastchapters(View view) {

        lastchapters_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        movieListViewModel.getLastChapters().observe(getActivity( ), new Observer<ChaptersResponse>( ) {
            @Override
            public void onChanged(ChaptersResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    if (cartoonResponse.getchapterModels() != null){
                        view.findViewById(R.id.akhiraladad).setVisibility(View.VISIBLE);
                        lastchapters_rv.setAdapter(new ChapterAdapter(getActivity(), cartoonResponse.getchapterModels(),0));

                    }
                }


            }
        });


    }

    private void Getlastepeadded(View view) {
        lastepeadded_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        movieListViewModel.getLastEpisodes().observe(getActivity( ), new Observer<EpisodesResponse>( ) {
            @Override
            public void onChanged(EpisodesResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    if (cartoonResponse.getEpesodModels() != null){
                        view.findViewById(R.id.akhiralakat).setVisibility(View.VISIBLE);
                        lastepeadded_rv.setAdapter(new EpisodeAdapter(getActivity(), cartoonResponse.getEpesodModels(),0));

                    }
                }


            }
        });


    }

    private void GetpupplarMoviesforthisMounth(View view) {
        topmoviemonth_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        movieListViewModel.topmovies().observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    if (cartoonResponse.getTvs() != null){
                        view.findViewById(R.id.axharaflamtoday).setVisibility(View.VISIBLE);
                        topmoviemonth_rv.setAdapter(new SeriesAdapter(getActivity(), cartoonResponse.getTvs(),0));

                    }
                }


            }
        });

    }

    private void Getpupplartvsforthisday(View view) {
        toptvtoday_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        SeriesAdapter ss = new SeriesAdapter(getActivity(),alltvs,0);
        toptvtoday_rv.setAdapter(ss);

        movieListViewModel.Toptvs().observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    view.findViewById(R.id.axhartvtpday).setVisibility(View.VISIBLE);
                    if (cartoonResponse.getTvs() != null){
                        alltvs.addAll(cartoonResponse.getTvs());
                        ss.notifyDataSetChanged();
                    }
                }



            }
        });


    }

    private void Getpin(View view) {


        pined_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        movieListViewModel.pinedcartoons().observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {

                if (cartoonResponse != null ){
                    if (cartoonResponse.getTvs() != null){
                        motabata.setVisibility(View.VISIBLE);
                        pined_rv.setAdapter(new SeriesAdapter(getActivity(), cartoonResponse.getTvs(),0));

                    }
                }


            }
        });






    }

    private void GetSlider(View view) {


        movieListViewModel.slider().observe(getActivity( ), new Observer<CartoonModel>( ) {
            @Override
            public void onChanged(CartoonModel slider) {

                if (slider != null ){
                    if (getActivity() != null){
                        Glide.with(getActivity()).load(slider.getPoster()).into(image_thumbnail);
                        Glide.with(getActivity()).load(slider.getLogo()).into(cartoon_logo);
                        geners_cartoon.setText(slider.getGener());

                        watchepenoew.setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View view) {
                                if (slider.getPlace() != null){
                                    Intent ss = new Intent( getActivity(), ShowInfoActivity.class );
                                    ss.putExtra("cartoon",slider);
                                    startActivity(ss);
                                }

                            }
                        });
                        view.findViewById(R.id.addtomylist).setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View v) {
                                clicksound(getActivity());
                                if (authManager.getUserInfo().getId() > 0){
                                    if (slider.getPlace() != null){
                                        AddToFavor_List(slider.getId());
                                    }

                                }else {
                                    Toast.makeText(getContext(), "سجل الدخول أولا", Toast.LENGTH_SHORT).show( );
                                }

                            }
                        });
                    }

                }


            }
        });



    }

    private void Top3btngo(View view) {

        view.findViewById(R.id.gototvsfrag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                series fragment = new series();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fream, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        view.findViewById(R.id.gotomovies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                movies fragment = new movies();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fream, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        view.findViewById(R.id.gotoComics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                ComicsList fragment = new ComicsList();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fream, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        view.findViewById(R.id.gotomanga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Mangas fragment = new Mangas();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fream, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        view.findViewById(R.id.gotoanime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Animes fragment = new Animes();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fream, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        view.findViewById(R.id.gotojstore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                Toast.makeText(getContext(), "سيتوفر قريبا", Toast.LENGTH_SHORT).show( );
            }
        });

      /*  view.findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                search fragment = new search();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fream, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });*/


    }


    private void initial(View view) {

        executorService = Executors.newSingleThreadExecutor();

        adViewLayout = view.findViewById(R.id.adViewLayout);
        search_btn = view.findViewById(R.id.search_btn);
        show_profile = view.findViewById(R.id.show_profile);
        image_thumbnail = view.findViewById(R.id.image_thumbnail);
        cartoon_logo = view.findViewById(R.id.cartoon_logo);
        geners_cartoon = view.findViewById(R.id.geners_cartoon);
        watchepenoew = view.findViewById(R.id.watchepenoew);
        motabata = view.findViewById(R.id.motabata);

        toptvtoday_rv = view.findViewById(R.id.toptvtoday_rv);
        pined_rv = view.findViewById(R.id.pined_rv);
        topmoviemonth_rv = view.findViewById(R.id.topmoviemonth_rv);
        lastepeadded_rv = view.findViewById(R.id.lastepeadded_rv);
        lastchapters_rv = view.findViewById(R.id.lastchapters_rv);
        newtvadded_rv = view.findViewById(R.id.newtvadded_rv);
        comicstopvisit_rv = view.findViewById(R.id.comicstopvisit_rv);
        mosttvandmovieaddedtofavourit_rv = view.findViewById(R.id.mosttvandmovieaddedtofavourit_rv);

        movieListViewModel = new ViewModelProvider(getActivity()).get(getAlltvsViewModel.class);
        //Utils.FixColumnsWidth(newtvadded_rv, 120, getContext());


        image_thumbnail.registerSensorManager();
        image_thumbnail.setScaledIntensities(true);

    }







    @Override
    public void onResume() {
        super.onResume( );
        image_thumbnail.registerSensorManager();
        image_thumbnail.setScaledIntensities(true);
    }

    @Override
    public void onPause() {
        super.onPause( );
        image_thumbnail.unregisterSensorManager();
    }

    @Override
    public void onStop() {
        super.onStop( );
        image_thumbnail.unregisterSensorManager();
    }

    private void AddToFavor_List(int id) {

        if (getActivity() !=  null){
            final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.addtofavouritlayout);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = MATCH_PARENT;
            lp.height = MATCH_PARENT;

            RecyclerView listView = dialog.findViewById(R.id.listfavourtadd);
            LinearLayout addtofav_btn = dialog.findViewById(R.id.addtofav_btn);
            String[] favou_list = new String[]{" المفضلة", "تم مشاهدتها","أرغب بمشاهدتها","أشاهدها حاليا","أكملها مستقبلا"};
            String[] favou_lis_backeend = new String[]{"favcartoon", "haswatched","iwantwatch","watchnow","watchlater"};

            List<addtofavModel> final_res = new ArrayList<>(  );
            for (String ss:  favou_list) {
                addtofavModel  addtofavModel =new addtofavModel(  );
                addtofavModel.setName(ss);
                final_res.add(addtofavModel);
            }


            listView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            AddToFavAdapter mAdapter = new AddToFavAdapter(final_res);
            listView.setAdapter(mAdapter);


            addtofav_btn.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    List<addtofavModel> stList = ((AddToFavAdapter) mAdapter)
                            .getselected();

                    List<String> selected = new ArrayList<>(  );

                    for (int i = 0; i < stList.size(); i++) {
                        addtofavModel singleStudent = stList.get(i);

                        if (singleStudent.isSelected( )) {
                            selected.add(favou_lis_backeend[i]);

                        }

                    }

                    if (selected.size() > 0){
                        for (String type:selected ) {
                            AddTofavD(id,type);
                        }
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "إختر مكان المفضلة", Toast.LENGTH_SHORT).show( );
                    }




                }
            });







            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }

    }

    private void AddTofavD(int id, String type) {
        AndroidNetworking.get(BASELINK+"cartoon/addFavorite")
                .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                .addQueryParameter("type", type)
                .addQueryParameter("tv_id", String.valueOf(id))
                .addHeaders(headers())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String msj = response.getString("message");
                            switch (msj){

                                case "added":
                                    // likeicon.setImageDrawable(getResources().getDrawable(R.drawable.check));
                                    Toast.makeText(getContext(), R.string.favouritadded_sent, Toast.LENGTH_SHORT).show();
                                    break;

                                case "exist":
                                    // likeicon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                                    Toast.makeText(getContext(), R.string.favouritremov_sent, Toast.LENGTH_SHORT).show();

                                    break;

                                case "error":
                                    Toast.makeText(getContext(), R.string.favouriot_error, Toast.LENGTH_SHORT).show();

                                    break;
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), R.string.favouriot_error, Toast.LENGTH_SHORT).show();

                    }
                });


    }

}