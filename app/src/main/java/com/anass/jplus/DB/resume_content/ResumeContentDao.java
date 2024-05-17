package com.anass.jplus.DB.resume_content;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ResumeContentDao {

    @Query("SELECT * FROM ResumeContent ORDER BY id DESC")
    List<ResumeContent> getResumeContents();

    @Insert
    void insert(ResumeContent resumeContent);

    @Query("UPDATE ResumeContent SET position = :position WHERE id = :id")
    void update(long position, int id);

    @Query("SELECT id FROM ResumeContent WHERE content_id = :value")
    int getResumeContentid(int value);

    @Query("DELETE FROM ResumeContent WHERE id = :id")
    void delete(int id);

    @Query("UPDATE ResumeContent SET source_type = :source_type, source_url = :source_url, content_type = :type WHERE id = :id")
    void updateSource(String source_type, String source_url, String type, int id);

    @Query("DELETE FROM ResumeContent")
    void clearDB();

    @Query("UPDATE ResumeContent SET duration = :duration WHERE id = :id")
    void updateDuration(long duration, int id);

    @Query("SELECT position FROM ResumeContent WHERE content_id = :value")
    long getResumeContentPosition(int value);

    @Query("SELECT source_type FROM ResumeContent WHERE content_id = :value")
    String getResumeContentSourceType(int value);

    @Query("SELECT source_url FROM ResumeContent WHERE content_id = :value")
    String getResumeContentSourceUrl(int value);

    @Query("SELECT name FROM ResumeContent WHERE content_id = :value")
    String getResumeContentName(int value);
}
