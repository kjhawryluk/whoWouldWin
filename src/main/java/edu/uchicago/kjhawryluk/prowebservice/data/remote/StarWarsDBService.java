package edu.uchicago.kjhawryluk.prowebservice.data.remote;

import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public interface StarWarsDBService {

    //TODO Look up how to use @GET from that site from the other day.

    @GET("people")
    Call<PeopleResponse> loadPeople();

}
