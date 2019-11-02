package edu.uchicago.kjhawryluk.whoWouldWin.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import edu.uchicago.kjhawryluk.whoWouldWin.FightTracker;
import edu.uchicago.kjhawryluk.whoWouldWin.data.StarWarsRepository;
import edu.uchicago.kjhawryluk.whoWouldWin.data.local.entity.FighterEntity;
import edu.uchicago.kjhawryluk.whoWouldWin.data.local.entity.PlanetEntity;

public class StarWarsViewModel extends AndroidViewModel {

    private StarWarsRepository mStarWarsRepository;
    private LiveData<List<FighterEntity>> fighters;
    private LiveData<List<PlanetEntity>> planets;

    public StarWarsViewModel(@NonNull Application application) {
        super(application);
        mStarWarsRepository = StarWarsRepository.getInstance(application);
        fighters = mStarWarsRepository.loadPeople();
        planets = mStarWarsRepository.loadPlanets();
    }

    public LiveData<List<FighterEntity>> getFighters() {
        return fighters;
    }

    public void setFighters(LiveData<List<FighterEntity>> fighters) {
        this.fighters = fighters;
    }

    public LiveData<List<PlanetEntity>> getPlanets() {
        return planets;
    }

    public void setPlanets(LiveData<List<PlanetEntity>> planets) {
        this.planets = planets;
    }

    public void startFight(Context activity, FightTracker fightTracker) {
        mStarWarsRepository.startFight(activity, fightTracker);
    }
}
