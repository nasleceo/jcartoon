package com.anass.jplus.backend.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.Responses.CastResponse;
import com.anass.jplus.backend.Responses.ChaptersResponse;
import com.anass.jplus.backend.Responses.CommentsResponse;
import com.anass.jplus.backend.Responses.EpisodesResponse;
import com.anass.jplus.backend.Responses.OneCartoonResponse;
import com.anass.jplus.backend.Responses.PostResponse;
import com.anass.jplus.backend.Responses.ReplayResponse;
import com.anass.jplus.backend.interfaces.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartoonRepository {


    private ApiService apiService;

    public CartoonRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }


    public LiveData<CartoonResponse> tvandcartoon(){
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.tvandcartoon().enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }


    public LiveData<CartoonResponse> getAllTVs(int page){
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.getalltvs(page).enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<CartoonResponse> FilterGener(String gener){
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.FilterGener(gener).enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<CartoonResponse> Search(String gener){
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.Search(gener).enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<CartoonResponse> getAllmovies(int page){
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.getAllmovies(page).enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<CastResponse> allcastapi(int page){
        MutableLiveData<CastResponse> data = new MutableLiveData<>(  );

        apiService.allcastapi(page).enqueue(new Callback<CastResponse>( ) {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<CartoonResponse> getAllcomics(int page){
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.getAllcomics(page).enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<OneCartoonResponse> oneTvorCartoonorComic(int page){
        MutableLiveData<OneCartoonResponse> data = new MutableLiveData<>(  );

        apiService.oneTvorCartoonorComic(page).enqueue(new Callback<OneCartoonResponse>( ) {
            @Override
            public void onResponse(Call<OneCartoonResponse> call, Response<OneCartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OneCartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<CartoonResponse> toptvtoday() {
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.toptvs().enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }


    public LiveData<CartoonResponse> pinedcartoons() {
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.pinedcartoons().enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<CartoonModel> slider() {
        MutableLiveData<CartoonModel> data = new MutableLiveData<>(  );

        apiService.slider().enqueue(new Callback<CartoonModel>( ) {
            @Override
            public void onResponse(Call<CartoonModel> call, Response<CartoonModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonModel> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<CartoonResponse> topmovies() {
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.topmovies().enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<EpisodesResponse> getLastEpisodes() {
        MutableLiveData<EpisodesResponse> data = new MutableLiveData<>(  );

        apiService.getLastEpisodes().enqueue(new Callback<EpisodesResponse>( ) {
            @Override
            public void onResponse(Call<EpisodesResponse> call, Response<EpisodesResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<EpisodesResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<ChaptersResponse> getLastChapters() {
        MutableLiveData<ChaptersResponse> data = new MutableLiveData<>(  );

        apiService.getLastChapters().enqueue(new Callback<ChaptersResponse>( ) {
            @Override
            public void onResponse(Call<ChaptersResponse> call, Response<ChaptersResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ChaptersResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<CartoonResponse> newrelease() {
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.newrelease().enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<CartoonResponse> topcomics() {
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.topcomics().enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<CartoonResponse> mosttvandmovieaddedtofavourit() {
        MutableLiveData<CartoonResponse> data = new MutableLiveData<>(  );

        apiService.mosttvandmovieaddedtofavourit().enqueue(new Callback<CartoonResponse>( ) {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<CommentsResponse> getCartoonCommentsByID(int cartoon_id,int page) {
        MutableLiveData<CommentsResponse> data = new MutableLiveData<>(  );

        apiService.getCartoonCommentsByID(cartoon_id,page).enqueue(new Callback<CommentsResponse>( ) {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {
                data.postValue(null);
            }
        });

        return data;

    }
    public LiveData<CommentsResponse> commentsofcastt(int cartoon_id,int page) {
        MutableLiveData<CommentsResponse> data = new MutableLiveData<>(  );

        apiService.commentsofcastt(cartoon_id,page).enqueue(new Callback<CommentsResponse>( ) {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<CommentsResponse> getPostCommentsByID(int cartoon_id,int page) {
        MutableLiveData<CommentsResponse> data = new MutableLiveData<>(  );

        apiService.getPostCommentsByID(cartoon_id,page).enqueue(new Callback<CommentsResponse>( ) {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {
                data.postValue(null);
            }
        });

        return data;

    }
    public LiveData<ReplayResponse> replaysofcomments(int cartoon_id, int page) {
        MutableLiveData<ReplayResponse> data = new MutableLiveData<>(  );

        apiService.replaysofcomments(cartoon_id,page).enqueue(new Callback<ReplayResponse>( ) {
            @Override
            public void onResponse(Call<ReplayResponse> call, Response<ReplayResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ReplayResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<PostResponse> posts(int page) {
        MutableLiveData<PostResponse> data = new MutableLiveData<>(  );

        apiService.posts(page).enqueue(new Callback<PostResponse>( ) {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                posts(page);

            }
        });

        return data;

    }
    public LiveData<PostResponse> mostliked(int page) {
        MutableLiveData<PostResponse> data = new MutableLiveData<>(  );

        apiService.mostliked(page).enqueue(new Callback<PostResponse>( ) {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                posts(page);
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<PostResponse> trendpost(String country,int page) {
        MutableLiveData<PostResponse> data = new MutableLiveData<>(  );

        apiService.trendpost(country,page).enqueue(new Callback<PostResponse>( ) {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                posts(page);
                data.setValue(null);
            }
        });

        return data;

    }
    public LiveData<PostResponse> random(int page) {
        MutableLiveData<PostResponse> data = new MutableLiveData<>(  );

        apiService.random(page).enqueue(new Callback<PostResponse>( ) {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                posts(page);
                data.setValue(null);
            }
        });

        return data;

    }

}
