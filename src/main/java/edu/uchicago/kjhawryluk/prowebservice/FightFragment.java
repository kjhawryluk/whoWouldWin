package edu.uchicago.kjhawryluk.prowebservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import edu.uchicago.kjhawryluk.prowebservice.adaptors.FightersAdaptor;
import edu.uchicago.kjhawryluk.prowebservice.data.Resource;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.ApiConstants;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.JSONParser;
import edu.uchicago.kjhawryluk.prowebservice.data.remote.model.PeopleResponse;
import edu.uchicago.kjhawryluk.prowebservice.viewmodels.PeopleViewModel;


public class FightFragment extends Fragment {

    private OnFightListener mListener;
    // @BindView(R.id.fighter1Spinner)
    Spinner mFighter1Spinner;
    @BindView(R.id.fighter2Spinner)
    Spinner mFighter2Spinner;
    private PeopleViewModel mPeopleViewModel;
    FightersAdaptor spinnerAdapter;
    public FightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fight, container, false);
        mPeopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel.class);
        spinnerAdapter = new FightersAdaptor(container.getContext(), android.R.layout.simple_spinner_item);
        mFighter1Spinner = root.findViewById(R.id.fighter1Spinner);
        mFighter1Spinner.setAdapter(spinnerAdapter);
        new PeopleListTask().execute(ApiConstants.ENDPOINT + "people/?page=1");
        //  mPeopleViewModel.getFighters().observe(this, (Observer<Resource<List<PersonEntity>>>)
        //         fighters -> spinnerAdapter.setPersonEntities(fighters));
        return root;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFightFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFightListener) {
            mListener = (OnFightListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFightListener {
        void onFightFragmentInteraction();
    }


    private class PeopleListTask extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                return new JSONParser().getJSONFromUrl(params[0], 2000);
            } catch (JSONException e) {
                Log.e("DO IN BACKGROUND", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PeopleResponse peopleResponse = gson.fromJson(jsonObject.toString(), PeopleResponse.class);
                mPeopleViewModel.setFighters(peopleResponse.getPersonResponses());
                spinnerAdapter.setPersonEntities(peopleResponse.getPersonResponses());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

//        public LiveData<Resource<List<PersonEntity>>> loadPeople() {
//
//            JSONParser jsonParser = new JSONParser();
//            try {
//                JSONObject peopleResponseJson = jsonParser.getJSONFromUrl(ApiConstants.ENDPOINT + "people/?page=1", ApiConstants.TIMEOUT_IN_SEC);
//                PeopleResponse peopleResponse = Gson
//            } catch (JSONException e) {
//                Log.e("RESPONSE ERROR", e.getMessage());
//            }
//        }
    }
}

