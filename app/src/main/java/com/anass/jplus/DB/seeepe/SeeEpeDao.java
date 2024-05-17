package com.anass.jplus.DB.seeepe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SeeEpeDao {

    @Insert
    void insert(SeeEpe SeeEpecont);

    @Query("SELECT id FROM SeeEpe WHERE content_id = :value")
    int getResumeContentid(int value);

    @Query("DELETE FROM SeeEpe WHERE id = :id")
    void delete(int id);

    @Query("UPDATE SeeEpe SET source_type = :source_type, source_url = :source_url WHERE id = :id")
    void updateSource(String source_type, String source_url, int id);

    @Query("SELECT name FROM SeeEpe WHERE content_id = :value")
    String getResumeContentName(int value);

}
