package edu.uchicago.kjhawryluk.prowebservice.data.remote;

import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PlanetResponse;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * https://android.jlelse.eu/rest-api-on-android-made-simple-or-how-i-learned-to-stop-worrying-and-love-the-rxjava-b3c2c949cad4
 */

public interface StarWarsRestService {

    @GET("people/?page={page_num}")
    Single<PeopleResponse> loadPeople(@Path("page_num") int page_num);

    @GET("planet/?page={page_num}")
    Single<PlanetResponse> loadPlanets(@Path("page_num") int page_num);
}
