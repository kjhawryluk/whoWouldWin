package edu.uchicago.kjhawryluk.prowebservice.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class StarWarsRepository {

    private final PeopleDao mPeopleDao;
    private final MovieDBService movieDBService;

    @Inject
    public StarWarsRepository(MovieDao mPeopleDao, MovieDBService movieDBService) {
        this.mPeopleDao = mPeopleDao;
        this.movieDBService = movieDBService;
    }

    public LiveData<Resource<List<MovieEntity>>> loadPopularMovies() {
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>() {

            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                mPeopleDao.saveMovies(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return mPeopleDao.loadMovies();
            }

            @NonNull
            @Override
            protected Call<MoviesResponse> createCall() {
                return movieDBService.loadMovies();
            }
        }.getAsLiveData();
    }

    public LiveData<MovieEntity> getMovie(int id){
        return mPeopleDao.getMovie(id);
    }
}
