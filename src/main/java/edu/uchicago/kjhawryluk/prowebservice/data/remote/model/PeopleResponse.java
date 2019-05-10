package edu.uchicago.kjhawryluk.prowebservice.data.remote.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;

public class PeopleResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("result")
    @Expose
    private List<PersonEntity> mPersonResponses = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
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

    public List<PersonEntity> getPersonResponses() {
        return mPersonResponses;
    }

    public void setPersonResponses(List<PersonEntity> personResponses) {
        this.mPersonResponses = personResponses;
    }

}
