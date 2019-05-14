
package edu.uchicago.kjhawryluk.prowebservice.data.remote.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;

public class PlanetResponse extends PagedResponse implements Serializable {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("results")
    @Expose
    private List<PlanetEntity> mPlanets = null;
    private final static long serialVersionUID = -8811980582448267184L;

    public PlanetResponse() {
        BASE_PATH = "https://swapi.co/api/planets/?page=";
    }

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

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<PlanetEntity> getPlanets() {
        return mPlanets;
    }

    public void setPlanets(List<PlanetEntity> planets) {
        this.mPlanets = planets;
    }


}