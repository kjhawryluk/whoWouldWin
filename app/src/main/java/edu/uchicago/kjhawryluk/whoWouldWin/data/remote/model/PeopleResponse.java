package edu.uchicago.kjhawryluk.whoWouldWin.data.remote.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.uchicago.kjhawryluk.whoWouldWin.data.local.entity.FighterEntity;

public class PeopleResponse extends PagedResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<FighterEntity> mFighterResponses = null;

    public PeopleResponse() {
        BASE_PATH = "https://swapi.co/api/people/?page=";
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<FighterEntity> getFighterResponses() {
        return mFighterResponses;
    }

    public void setFighterResponses(List<FighterEntity> fighterResponses) {
        this.mFighterResponses = fighterResponses;
    }

}
