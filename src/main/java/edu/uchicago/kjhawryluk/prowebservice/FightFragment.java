package edu.uchicago.kjhawryluk.prowebservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import edu.uchicago.kjhawryluk.prowebservice.adaptors.FightersAdaptor;
import edu.uchicago.kjhawryluk.prowebservice.data.Resource;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.viewmodels.PeopleViewModel;


public class FightFragment extends Fragment {

    private OnFightListener mListener;
    @BindView(R.id.fighter1Spinner)
    Spinner mFighter1Spinner;
    @BindView(R.id.fighter2Spinner)
    Spinner mFighter2Spinner;
    private PeopleViewModel mPeopleViewModel;

    public FightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fight, container, false);
        mPeopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel.class);
        FightersAdaptor spinnerAdapter = new FightersAdaptor(container.getContext(), android.R.layout.simple_spinner_item);
        mFighter1Spinner.setAdapter(spinnerAdapter);
        mPeopleViewModel.getFighters().observe(this, (Observer<Resource<List<PersonEntity>>>)
                fighters -> spinnerAdapter.setPersonEntities(fighters));
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
}

