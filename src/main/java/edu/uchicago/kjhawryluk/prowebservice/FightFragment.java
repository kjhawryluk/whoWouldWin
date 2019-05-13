package edu.uchicago.kjhawryluk.prowebservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import edu.uchicago.kjhawryluk.prowebservice.adaptors.FightersAdaptor;
import edu.uchicago.kjhawryluk.prowebservice.data.Resource;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.viewmodels.PeopleViewModel;


public class FightFragment extends Fragment {

    public static final String FIGHTER1 = "FIGHTER1";
    public static final String FIGHTER2 = "FIGHTER2";
    private OnFightListener mListener;
    FightersAdaptor spinnerAdapter;
    Spinner mFighter1Spinner;
    @BindView(R.id.fighter2Spinner)
    Spinner mFighter2Spinner;
    private PeopleViewModel mPeopleViewModel;

    public FightFragment() {
        // Required empty public constructor
    }

    public static FightFragment newInstance() {
        return new FightFragment();
    }

    public static FightFragment newInstance(Serializable fighter1, Serializable fighter2) {
        FightFragment fragment = new FightFragment();
        Bundle args = new Bundle();
        args.putSerializable(FIGHTER1, fighter1);
        args.putSerializable(FIGHTER2, fighter2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fight, container, false);
        mPeopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel.class);
        spinnerAdapter = new FightersAdaptor(container.getContext(), android.R.layout.simple_spinner_item);
        mFighter1Spinner = root.findViewById(R.id.fighter1Spinner);
        mFighter2Spinner = root.findViewById(R.id.fighter2Spinner);

        //Using the same adaptor to listen for new fighters.
        mFighter1Spinner.setAdapter(spinnerAdapter);
        mFighter2Spinner.setAdapter(spinnerAdapter);
        mPeopleViewModel.getFighters().observe(this, (Observer<List<PersonEntity>>)
                fighters -> {
                    spinnerAdapter.setPersonEntities(fighters);
                    setSpinnerValues();
                });

        mFighter1Spinner.setOnItemSelectedListener(new SpinnerSelection(container));
        mFighter2Spinner.setOnItemSelectedListener(new SpinnerSelection(container));

        return root;
    }


    private class SpinnerSelection implements AdapterView.OnItemSelectedListener {
        ViewGroup container;

        public SpinnerSelection(ViewGroup container) {
            this.container = container;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getCount() > 0) {
                ((OnFightListener) container.getContext()).setFighter1((PersonEntity) mFighter1Spinner.getSelectedItem());
                ((OnFightListener) container.getContext()).setFighter2((PersonEntity) mFighter2Spinner.getSelectedItem());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void setSpinnerValues() {
        if (getArguments() != null) {
            Serializable fighter1 = getArguments().getSerializable(FIGHTER1);
            Serializable fighter2 = getArguments().getSerializable(FIGHTER2);
            if (fighter1 != null) {
                mFighter1Spinner.setSelection(spinnerAdapter.getPosition(fighter1.toString()));
            }

            if (fighter2 != null) {
                mFighter2Spinner.setSelection(spinnerAdapter.getPosition(fighter2.toString()));
            }
        }
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

        public PersonEntity getFighter1();

        public void setFighter1(PersonEntity fighter1);

        public PersonEntity getFighter2();

        public void setFighter2(PersonEntity fighter2);
    }
}

