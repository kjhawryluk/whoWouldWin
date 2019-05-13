package edu.uchicago.kjhawryluk.prowebservice.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import edu.uchicago.kjhawryluk.prowebservice.data.local.StarWarsDatabase;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.ApiConstants;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;

import java.net.InetAddress;
import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.StarWarsRestService;
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
    private final StarWarsRestService mStarWarsRestService;
    private StarWarsDatabase mStarWarsDatabase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public StarWarsRepository(Application application) {
        this.mStarWarsDatabase = StarWarsDatabase.getDatabase(application);
        this.mPeopleDao = this.mStarWarsDatabase.mPeopleDao();
        this.mStarWarsRestService = getStarWarsRestService();
        if (isInternetAvailable()) {
            mStarWarsRestService.loadPeople()
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
                        }

                        @Override
                        public void onError(Throwable e) {
                            // oops, we best show some error message
                            Log.e("REPSONSE ERROR", e.getMessage());
                        }

                    });
        }
//        if (!compositeDisposable.isDisposed()) {
//            compositeDisposable.dispose();
//        }
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



    private StarWarsRestService getStarWarsRestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(StarWarsRestService.class);
    }

//    public LiveData<Resource<List<PersonEntity>>> loadPeople() {
//        return new NetworkBoundResource<List<PersonEntity>, PeopleResponse>() {
//
//            @Override
//            protected void saveCallResult(@NonNull PeopleResponse item) {
//                mPeopleDao.savePeople(item.getPersonResponses());
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<List<PersonEntity>> loadFromDb() {
//                return mPeopleDao.loadPeople();
//            }
//
//            @NonNull
//            @Override
//            protected Call<PeopleResponse> createCall() {
//                return mStarWarsRestService.loadPeople();
//            }
//        }.getAsLiveData();
//    }


    public LiveData<List<PersonEntity>> loadPeople() {
        return mPeopleDao.loadPeople();
    }

    public LiveData<PersonEntity> getPerson(String name) {
        return mPeopleDao.getPerson(name);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
