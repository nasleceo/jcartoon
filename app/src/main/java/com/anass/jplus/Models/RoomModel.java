package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomModel implements Serializable
{

    @SerializedName("id")
    @Expose

    private String  id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("who_create_room_user_id")
    @Expose
    private Integer whoCreateRoomUserId;
    @SerializedName("invitaion_code")
    @Expose
    private String invitaionCode;
    @SerializedName("conversation_id")
    @Expose
    private Integer conversationId;
    @SerializedName("time_creted")
    @Expose
    private String timeCreted;
    @SerializedName("tv_id")
    @Expose
    private int tvId;
    @SerializedName("season_id")
    @Expose
    private int seasonId;
    @SerializedName("epe_id")
    @Expose
    private int epeId;
    @SerializedName("type_ispublic_private")
    @Expose
    private String typeIspublicPrivate;
    @SerializedName("number_limit")
    @Expose
    private Integer numberLimit;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public RoomModel(String id, String title, Integer whoCreateRoomUserId, String invitaionCode,  String timeCreted, int tvId, int seasonId, int epeId, String typeIspublicPrivate, Integer numberLimit) {
        this.id = id;
        this.title = title;
        this.whoCreateRoomUserId = whoCreateRoomUserId;
        this.invitaionCode = invitaionCode;
        this.timeCreted = timeCreted;
        this.tvId = tvId;
        this.seasonId = seasonId;
        this.epeId = epeId;
        this.typeIspublicPrivate = typeIspublicPrivate;
        this.numberLimit = numberLimit;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWhoCreateRoomUserId() {
        return whoCreateRoomUserId;
    }

    public void setWhoCreateRoomUserId(Integer whoCreateRoomUserId) {
        this.whoCreateRoomUserId = whoCreateRoomUserId;
    }

    public String getInvitaionCode() {
        return invitaionCode;
    }

    public void setInvitaionCode(String invitaionCode) {
        this.invitaionCode = invitaionCode;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public String getTimeCreted() {
        return timeCreted;
    }

    public void setTimeCreted(String timeCreted) {
        this.timeCreted = timeCreted;
    }

    public int getTvId() {
        return tvId;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getEpeId() {
        return epeId;
    }

    public void setEpeId(int epeId) {
        this.epeId = epeId;
    }

    public String getTypeIspublicPrivate() {
        return typeIspublicPrivate;
    }

    public void setTypeIspublicPrivate(String typeIspublicPrivate) {
        this.typeIspublicPrivate = typeIspublicPrivate;
    }

    public Integer getNumberLimit() {
        return numberLimit;
    }

    public void setNumberLimit(Integer numberLimit) {
        this.numberLimit = numberLimit;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}