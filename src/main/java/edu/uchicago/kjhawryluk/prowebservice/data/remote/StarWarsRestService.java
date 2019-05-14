package edu.uchicago.kjhawryluk.prowebservice.data.remote;

import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PlanetResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * https://android.jlelse.eu/rest-api-on-android-made-simple-or-how-i-learned-to-stop-worrying-and-love-the-rxjava-b3c2c949cad4
 */

public interface StarWarsRestService {

    @GET("people/")
    Single<PeopleResponse> loadPeople(@Query("page") int page_num);

    @GET("planets/")
    Single<PlanetResponse> loadPlanets(@Query("page") int page_num);
}
