package edu.uchicago.kjhawryluk.whoWouldWin;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import edu.uchicago.kjhawryluk.whoWouldWin.R;
import edu.uchicago.kjhawryluk.whoWouldWin.adaptors.FightersAdaptor;
import edu.uchicago.kjhawryluk.whoWouldWin.adaptors.PlanetsAdaptor;
import edu.uchicago.kjhawryluk.whoWouldWin.data.local.entity.FighterEntity;
import edu.uchicago.kjhawryluk.whoWouldWin.data.local.entity.PlanetEntity;
import edu.uchicago.kjhawryluk.whoWouldWin.viewmodels.StarWarsViewModel;


public class FightFragment extends Fragment {

    public static final String FIGHTER1 = "FIGHTER1";
    public static final String FIGHTER2 = "FIGHTER2";
    public static final String PLANET = "PLANET";
    private OnFightListener mListener;
    FightersAdaptor mFightersAdaptor;
    PlanetsAdaptor mPlanetsAdaptor;
    Spinner mFighter1Spinner;
    Spinner mFighter2Spinner;
    Spinner mPlanetSpinner;
    Button mFightButton;
    private StarWarsViewModel mStarWarsViewModel;

    public FightFragment() {
        // Required empty public constructor
    }

    public static FightFragment newInstance() {
        return new FightFragment();
    }

    public static FightFragment newInstance(Serializable fighter1, Serializable fighter2, Serializable planet) {
        FightFragment fragment = new FightFragment();
        Bundle args = new Bundle();
        args.putSerializable(FIGHTER1, fighter1);
        args.putSerializable(FIGHTER2, fighter2);
        args.putSerializable(PLANET, planet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fight, container, false);
        mStarWarsViewModel = ViewModelProviders.of(this).get(StarWarsViewModel.class);
        mFightersAdaptor = new FightersAdaptor(container.getContext(), android.R.layout.simple_spinner_item);
        mPlanetsAdaptor = new PlanetsAdaptor(container.getContext(), android.R.layout.simple_spinner_item);
        mFighter1Spinner = root.findViewById(R.id.fighter1Spinner);
        mFighter2Spinner = root.findViewById(R.id.fighter2Spinner);
        mPlanetSpinner = root.findViewById(R.id.planetSpinner);
        mFightButton = root.findViewById(R.id.fightButton);

        //Using the same adaptor to listen for new fighters.
        mFighter1Spinner.setAdapter(mFightersAdaptor);
        mFighter2Spinner.setAdapter(mFightersAdaptor);
        mPlanetSpinner.setAdapter(mPlanetsAdaptor);
        mStarWarsViewModel.getFighters().observe(this, (Observer<List<FighterEntity>>)
                fighters -> {
                    mFightersAdaptor.setPersonEntities(fighters);
                    setSpinnerValues();
                });
        mStarWarsViewModel.getPlanets().observe(this, (Observer<List<PlanetEntity>>)
                planetEntities -> {
                    mPlanetsAdaptor.setPlanetEntities(planetEntities);
                    setSpinnerValues();
                });

        mFighter1Spinner.setOnItemSelectedListener(new SpinnerSelection());
        mFighter2Spinner.setOnItemSelectedListener(new SpinnerSelection());
        mPlanetSpinner.setOnItemSelectedListener(new SpinnerSelection());

        //TODO Add fight fight_button on click mechanism to show winner.
        mFightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.startFight();
                }
            }
        });
        return root;
    }


    private class SpinnerSelection implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getCount() > 0) {
                mListener.setFighter1((FighterEntity) mFighter1Spinner.getSelectedItem());
                mListener.setFighter2((FighterEntity) mFighter2Spinner.getSelectedItem());
                mListener.setPlanet((PlanetEntity) mPlanetSpinner.getSelectedItem());
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
            Serializable planet = getArguments().getSerializable(PLANET);
            if (fighter1 != null) {
                mFighter1Spinner.setSelection(mFightersAdaptor.getPosition(fighter1.toString()));
            }

            if (fighter2 != null) {
                mFighter2Spinner.setSelection(mFightersAdaptor.getPosition(fighter2.toString()));
            }


            if (planet != null) {
                mPlanetSpinner.setSelection(mPlanetsAdaptor.getPosition(planet.toString()));
            }
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

        public FighterEntity getFighter1();

        public void setFighter1(FighterEntity fighter1);

        public FighterEntity getFighter2();

        public void setFighter2(FighterEntity fighter2);

        public void setPlanet(PlanetEntity planet);

        public void startFight();
    }
}

