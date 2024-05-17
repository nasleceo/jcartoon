package com.anass.jplus.backend.interfaces;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.Models.NewsModel;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.Models.SkipModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.Models.adsmodel;
import com.anass.jplus.backend.Responses.AdsupdateResponse;
import com.anass.jplus.backend.Responses.AuthResponse;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.Responses.CastResponse;
import com.anass.jplus.backend.Responses.ChapterResponse;
import com.anass.jplus.backend.Responses.ChaptersResponse;
import com.anass.jplus.backend.Responses.CommentsResponse;
import com.anass.jplus.backend.Responses.EpisodesResponse;
import com.anass.jplus.backend.Responses.FavoutirResponse;
import com.anass.jplus.backend.Responses.LikeCountsResponse;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.Responses.NotificationResponse;
import com.anass.jplus.backend.Responses.OneCartoonResponse;
import com.anass.jplus.backend.Responses.PagesResponse;
import com.anass.jplus.backend.Responses.PostResponse;
import com.anass.jplus.backend.Responses.PostVisitResponse;
import com.anass.jplus.backend.Responses.ReplayResponse;
import com.anass.jplus.backend.Responses.SeasonResponse;
import com.anass.jplus.backend.Responses.newsresponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cartoon/tvandcartoon")
    Call<CartoonResponse> tvandcartoon();

    @GET("cartoon/tvs")
    Call<CartoonResponse> getalltvs(@Query("page") int page);

    @GET("cartoon/movies")
    Call<CartoonResponse> getAllmovies(@Query("page") int page);

    @GET("cartoon/comics")
    Call<CartoonResponse> getAllcomics(@Query("page") int page);

    @GET("cartoon/tvs/seasons/{id}")
    Call<SeasonResponse> getSeasons(@Path("id") int id);

    @GET("cartoon/tvs/episodes/{id}")
    Call<List<EpesodModel>> getEpisodes(@Path("id") int id);

    @GET("cartoon/onepe/{id}")
    Call<EpesodModel> getoneEpisode(@Path("id") int id);

    @GET("cartoon/movies/getMovieVideos/{id}")
    Call<List<EpesodModel>> getMovieEpisodes(@Path("id") int id);

    @GET("cartoon/searchFilter")
    Call<CartoonResponse> FilterGener(@Query("gener") String gener);

    @GET("cartoon/searchFilter")
    Call<CartoonResponse> Search(@Query("title") String title);

    @GET("cartoon/oneTvorCartoonorComic/{id}")
    Call<OneCartoonResponse> oneTvorCartoonorComic(@Path("id") int id);
    @GET("cartoon/comics/chapters/{id}")
    Call<ChapterResponse> chapters(@Path("id") int id);

    @GET("cartoon/finduser/{id}")
    Call<UserModel> finduser(@Path("id") int id);

    @GET("cartoon/comics/chapterspages/{id}")
    Call<PagesResponse> pages(@Path("id") int id);
    @GET("cartoon/toptvs")
    Call<CartoonResponse> toptvs();

    @GET("cartoon/allcastapi")
    Call<CastResponse> allcastapi(@Query("page") int page);

    @GET("cartoon/pinedcartoons")
    Call<CartoonResponse> pinedcartoons();


    @GET("cartoon/alladsidandnative")
    Call<AdsupdateResponse> alladsidandnative();

    @GET("cartoon/slider")
    Call<CartoonModel> slider();

    @GET("cartoon/topmovies")
    Call<CartoonResponse> topmovies();

    @GET("cartoon/skipapi")
    Call<SkipModel> skipapi();

    @GET("cartoon/newrelease")
    Call<CartoonResponse> newrelease();
    @GET("cartoon/getLastEpisodes")
    Call<EpisodesResponse> getLastEpisodes();

    @GET("cartoon/comics/getLastChapters")
    Call<ChaptersResponse> getLastChapters();

    @GET("cartoon/topcomics")
    Call<CartoonResponse> topcomics();

    @GET("cartoon/allnews")
    Call<newsresponse> allnews();

    @GET("cartoon/mosttvandmovieaddedtofavourit")
    Call<CartoonResponse> mosttvandmovieaddedtofavourit();

    @GET("cartoon/commentsofcartoon/{id}")
    Call<CommentsResponse> getCartoonCommentsByID(@Path("id") int id,@Query("page") int page);

    @GET("cartoon/commentsofcastt/{id}")
    Call<CommentsResponse> commentsofcastt(@Path("id") int id,@Query("page") int page);

    @GET("cartoon/commentsofpost/{id}")
    Call<CommentsResponse> getPostCommentsByID(@Path("id") int id,@Query("page") int page);

    @GET("cartoon/commentsofnews/{id}")
    Call<CommentsResponse> getNewsCommentsByID(@Path("id") int id,@Query("page") int page);

    @GET("cartoon/likeunlikecomment")
    Call<MessageResponse> LikeComment(@Query("comment_id") int comment_id,@Query("user_id") int user_id);

    @GET("cartoon/CheckLikeComment")
    Call<MessageResponse> CheckLikeComment(@Query("comment_id") int comment_id,@Query("user_id") int user_id);

   @GET("cartoon/deletcomment/{id}")
    Call<MessageResponse> deletcomment(@Path("id") int id);

    @GET("cartoon/editcomment/{id}")
    Call<MessageResponse> editcomment(@Path("id") int id,@Query("content") String content);

    @GET("cartoon/replaysofcomments/{id}")
    Call<ReplayResponse> replaysofcomments(@Path("id") int id, @Query("page") int page);


    @GET("cartoon/posts")
    Call<PostResponse> posts(@Query("page") int page);

    @GET("cartoon/getfavouritPosts/{user_id}")
    Call<PostResponse> getfavouritPosts(@Path("user_id") int user_id);

    @GET("cartoon/myposts/{user_id}")
    Call<PostResponse> myposts(@Path("user_id") int user_id);

    @GET("cartoon/getFavouritcast/{user_id}")
    Call<CastResponse> getFavouritcast(@Path("user_id") int user_id);

    @GET("cartoon/mostliked")
    Call<PostResponse> mostliked(@Query("page") int page);

    @GET("cartoon/trendpost/{country}")
    Call<PostResponse> trendpost(@Path("country") String id,@Query("page") int page);

    @GET("cartoon/random")
    Call<PostResponse> random(@Query("page") int page);

    @GET("cartoon/visitpost/{id}")
    Call<PostVisitResponse> visitpost(@Path("id") int id);

    @GET("cartoon/notifications/{id}")
    Call<NotificationResponse> notifications(@Path("id") int id);
    @POST("auth/login")
    Call<AuthResponse> login (@Query("email") String email, @Query("password") String password);

   @POST("auth/checkusernamejcartoon")
    Call<MessageResponse> checkusernamejcartoon (@Query("userspecial_name") String userspecial_name);

    @POST("auth/checkuseremail")
    Call<MessageResponse> checkuseremail (@Query("email") String email);

    @Multipart
    @POST("auth/tasjil")
    Call<AuthResponse> signin (
            @Query("userspecial_name") String userspecial_name,
            @Query("name") String name,
            @Query("email") String email,
            @Query("country") String country ,
            @Query("password") String password,
            @Query("device_id") String device_id ,
            @Part MultipartBody.Part image
            );

    @Multipart
    @POST("cartoon/editacc")
    Call<AuthResponse> editprofi (
            @Query("id") int id,
            @Query("profile_desc") String profile_desc,
            @Query("userspecial_name") String userspecial_name,
            @Query("name") String name,
            @Query("country") String country ,
            @Query("device_id") String device_id ,
            @Part MultipartBody.Part image
    );
    @Multipart
    @POST("cartoon/addpost")
    Call<MessageResponse> addpost (
            @Query("user_id") String user_id,
            @Query("text") String text,
            @Query("type") String type,
            @Query("post_Time") String post_Time ,
            @Query("ishark") String ishark,
            @Query("country") String country ,
            @Query("activecomments") String activecomments ,
            @Query("tv_id") String tv_id ,
            @Query("tawsiat_tv_id") String tawsiat_tv_id ,
            @Query("cast_id") String cast_id ,
            @Query("cast_id_2") String cast_id_2 ,
            @Query("state") String state ,
            @Part MultipartBody.Part image
    );


    @GET("cartoon/Likemowajahacount/{id}")
    Call<LikeCountsResponse> getLikemowajahaCounts(@Path("id") int id );

    @GET("cartoon/Followunfollowuser")
    Call<MessageResponse> Followunfollowuser(  @Query("user_id") String user_id,
                                                  @Query("target_user_id") String target_user_id);

    @GET("cartoon/CheckFollow")
    Call<MessageResponse> CheckFollow(  @Query("user_id") int user_id,
                                               @Query("target_user_id") int target_user_id);

    @GET("cartoon/getfollowings/{id}")
    Call<Long> getfollowings(@Path("id") int id);

    @GET("cartoon/getfollowers/{id}")
    Call<Long> getfollowers(@Path("id") int id);

    @GET("cartoon/commentcount/{id}")
    Call<Long> commentcount(@Path("id") int id);

    @GET("cartoon/postcount/{id}")
    Call<Long> postcount(@Path("id") int id);

    @GET("cartoon/getFavouritCartoon/{user_id}")
    Call<FavoutirResponse> getFavouritCartoon(@Path("user_id") int user_id, @Query("type") String type);


    @GET("cartoon/CheckLikeposts")
    Call<MessageResponse> CheckLikeposts(  @Query("post_id") int post_id,
                                        @Query("user_id") int user_id);

    @GET("cartoon/likeunlikepost")
    Call<MessageResponse> likeunlikepost(  @Query("post_id") int post_id,
                                           @Query("user_id") int user_id);

    @GET("cartoon/savecast")
    Call<MessageResponse> savecast(  @Query("cast_id") int cast_id,
                                           @Query("user_id") int user_id);

    @GET("cartoon/Checksavecast")
    Call<MessageResponse> Checksavecast(  @Query("cast_id") int cast_id,
                                           @Query("user_id") int user_id);

    @GET("cartoon/castcountfav/{id}")
    Call<Long> castcountfav(@Path("id") int id);

    @GET("cartoon/getlikecountpost/{post_id}")
    Call<Long> getlikecountpost(@Path("post_id") int post_id);


    @GET("cartoon/watchlog")
    Call<MessageResponse> watchlog(
            @Query("content_id") int content_id
            ,@Query("content_type") int content_type,
            @Query("epe_id") int epe_id,
            @Query("user_id") String user_id);

    @GET("cartoon/deviceslog")
    Call<MessageResponse> deviceslog(
            @Query("device_id") String device_id
            ,@Query("open_date") String open_date
            ,@Query("open_time") String open_time);

}
