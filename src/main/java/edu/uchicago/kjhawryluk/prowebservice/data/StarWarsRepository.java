package edu.uchicago.kjhawryluk.prowebservice.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import edu.uchicago.kjhawryluk.prowebservice.data.local.StarWarsDatabase;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.StarWarsDBService;
import retrofit2.Call;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class StarWarsRepository {

    private final PeopleDao mPeopleDao;
    private final StarWarsDBService mStarWarsDBService;
    private StarWarsDatabase mStarWarsDatabase;

    public StarWarsRepository(Application application) {
        this.mStarWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.mPeopleDao = this.mStarWarsDatabase.mPeopleDao();
        this.mStarWarsDBService = starWarsDBService;
    }

    public LiveData<Resource<List<PersonEntity>>> loadPeople() {
        return new NetworkBoundResource<List<PersonEntity>, PeopleResponse>() {

            @Override
            protected void saveCallResult(@NonNull PeopleResponse item) {
                mPeopleDao.savePeople(item.getPersonResponses());
            }

            @NonNull
            @Override
            protected LiveData<List<PersonEntity>> loadFromDb() {
                return mPeopleDao.loadPeople();
            }

            @NonNull
            @Override
            protected Call<PeopleResponse> createCall() {
                return mStarWarsDBService.loadPeople();
            }
        }.getAsLiveData();
    }

    public LiveData<PersonEntity> getPerson(String name) {
        return mPeopleDao.getPerson(name);
    }
}
