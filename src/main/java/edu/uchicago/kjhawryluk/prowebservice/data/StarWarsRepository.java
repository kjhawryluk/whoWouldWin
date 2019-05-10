package edu.uchicago.kjhawryluk.prowebservice.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import edu.uchicago.kjhawryluk.prowebservice.data.local.StarWarsDatabase;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.ApiConstants;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.StarWarsRestService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class StarWarsRepository {

    private final PeopleDao mPeopleDao;
    private final StarWarsRestService mStarWarsRestService;
    private StarWarsDatabase mStarWarsDatabase;

    public StarWarsRepository(Application application) {
        this.mStarWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.mPeopleDao = this.mStarWarsDatabase.mPeopleDao();
        this.mStarWarsRestService = getStarWarsRestService();
    }

    private StarWarsRestService getStarWarsRestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(StarWarsRestService.class);
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
                return mStarWarsRestService.loadPeople();
            }
        }.getAsLiveData();
    }

    public LiveData<PersonEntity> getPerson(String name) {
        return mPeopleDao.getPerson(name);
    }
}
