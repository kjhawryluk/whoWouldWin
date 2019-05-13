package edu.uchicago.kjhawryluk.prowebservice.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.Resource;
import edu.uchicago.kjhawryluk.prowebservice.data.StarWarsRepository;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;

public class PeopleViewModel extends AndroidViewModel {

    private StarWarsRepository mStarWarsRepository;
    private LiveData<List<PersonEntity>> fighters;

    public PeopleViewModel(@NonNull Application application) {
        super(application);
        mStarWarsRepository = new StarWarsRepository(application);
        fighters = mStarWarsRepository.loadPeople();
    }

    public LiveData<List<PersonEntity>> getFighters() {
        return fighters;
    }

    public void setFighters(LiveData<List<PersonEntity>> fighters) {
        this.fighters = fighters;
    }
}
