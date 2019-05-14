package edu.uchicago.kjhawryluk.prowebservice.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import edu.uchicago.kjhawryluk.prowebservice.data.local.StarWarsDatabase;
import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PlanetDao;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.ApiConstants;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;

import java.net.InetAddress;
import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.StarWarsRestService;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PlanetResponse;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class StarWarsRepository {

    private final PeopleDao mPeopleDao;
    private final PlanetDao mPlanetDao;
    private final StarWarsRestService mStarWarsRestService;
    private StarWarsDatabase mStarWarsDatabase;
    public static final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public StarWarsRepository(Application application) {
        this.mStarWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.mPeopleDao = this.mStarWarsDatabase.mPeopleDao();
        this.mPlanetDao = this.mStarWarsDatabase.mPlanetDao();
        this.mStarWarsRestService = getStarWarsRestService();
        if (isInternetAvailable(application)) {
            fetchPeople(1);
            fetchPlanets(1);
        }
    }

    private void fetchPeople(int pageNum) {
        mStarWarsRestService.loadPeople(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PeopleResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(PeopleResponse people) {
                        // data is ready and we can update the UI
                        saveResult(people);
                        int nextPageNum = people.getNextPageNum();
                        if (nextPageNum > -1)
                            fetchPeople(nextPageNum);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // oops, we best show some error message
                        Log.e("REPSONSE ERROR", e.getMessage());
                    }

                });
    }

    private void fetchPlanets(int pageNum) {
        mStarWarsRestService.loadPlanets(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PlanetResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(PlanetResponse planets) {
                        // data is ready and we can update the UI
                        saveResult(planets);
                        int nextPageNum = planets.getNextPageNum();
                        if (nextPageNum > -1)
                            fetchPeople(nextPageNum);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // oops, we best show some error message
                        Log.e("REPSONSE ERROR", e.getMessage());
                    }

                });
    }


    void saveResult(PeopleResponse response) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                List<Long> indices = mPeopleDao.savePeople(response.getPersonResponses());
                return true;
            }
        }.execute();
    }

    void saveResult(PlanetResponse response) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                List<Long> indices = mPlanetDao.savePlanets(response.getPlanets());
                return true;
            }
        }.execute();
    }


    private StarWarsRestService getStarWarsRestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(StarWarsRestService.class);
    }

    public LiveData<List<PersonEntity>> loadPeople() {
        return mPeopleDao.loadPeople();
    }

    public LiveData<List<PlanetEntity>> loadPlanets() {
        return mPlanetDao.loadPlanets();
    }

    public LiveData<PersonEntity> getPerson(String name) {
        return mPeopleDao.getPerson(name);
    }

    /**
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
     *
     * @return
     */
    public boolean isInternetAvailable(Application application) {
        ConnectivityManager cm =
                (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
