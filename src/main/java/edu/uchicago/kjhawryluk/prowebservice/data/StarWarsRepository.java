package edu.uchicago.kjhawryluk.prowebservice.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.MovieDBService;
import retrofit2.Call;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class StarWarsRepository {

    private final PeopleDao mPeopleDao;
    private final MovieDBService movieDBService;

    //@Inject
    public StarWarsRepository(PeopleDao mPeopleDao, MovieDBService movieDBService) {
        this.mPeopleDao = mPeopleDao;
        this.movieDBService = movieDBService;
    }

    public LiveData<Resource<List<PersonEntity>>> loadPopularMovies() {
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
                return movieDBService.loadPeople();
            }
        }.getAsLiveData();
    }

    public LiveData<PersonEntity> getPerson(String name) {
        return mPeopleDao.getPerson(name);
    }
}
