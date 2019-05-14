package edu.uchicago.kjhawryluk.prowebservice.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.Resource;
import edu.uchicago.kjhawryluk.prowebservice.data.StarWarsRepository;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;

public class StarWarsViewModel extends AndroidViewModel {

    private StarWarsRepository mStarWarsRepository;
    private LiveData<List<PersonEntity>> fighters;
    private LiveData<List<PlanetEntity>> planets;

    public StarWarsViewModel(@NonNull Application application) {
        super(application);
        mStarWarsRepository = new StarWarsRepository(application);
        fighters = mStarWarsRepository.loadPeople();
        planets = mStarWarsRepository.loadPlanets();
    }

    public LiveData<List<PersonEntity>> getFighters() {
        return fighters;
    }

    public void setFighters(LiveData<List<PersonEntity>> fighters) {
        this.fighters = fighters;
    }

    public LiveData<List<PlanetEntity>> getPlanets() {
        return planets;
    }

    public void setPlanets(LiveData<List<PlanetEntity>> planets) {
        this.planets = planets;
    }
}
