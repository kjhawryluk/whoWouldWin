package edu.uchicago.kjhawryluk.whoWouldWin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uchicago.kjhawryluk.whoWouldWin.R;
import edu.uchicago.kjhawryluk.whoWouldWin.data.local.entity.FighterEntity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FighterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FighterFragment extends Fragment {
    private static final String FIGHTER = "fighter";
    public static final String IS_WINNER = "isWinner";

    private FighterEntity mFighter;
    TextView fighterNameTextView;
    TextView heightTextView;
    TextView massTextView;
    TextView hairColorTextView;
    TextView eyeColorTextView;
    TextView birthYearTextView;
    TextView genderTextView;
    TextView winnerTextView;

    public FighterFragment() {
    }


    public static FighterFragment newInstance(FighterEntity fighter) {
        FighterFragment fragment = new FighterFragment();
        Bundle args = new Bundle();
        args.putSerializable(FIGHTER, fighter);
        fragment.setArguments(args);
        return fragment;
    }

    public static FighterFragment newInstance(FighterEntity fighter, boolean isWinner) {
        FighterFragment fragment = new FighterFragment();
        Bundle args = new Bundle();
        args.putSerializable(FIGHTER, fighter);
        args.putBoolean(IS_WINNER, isWinner);
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
        View root = inflater.inflate(R.layout.fragment_fighter, container, false);
        fighterNameTextView = root.findViewById(R.id.fighterNameTextView);
        heightTextView = root.findViewById(R.id.heightTextView);
        massTextView = root.findViewById(R.id.massTextView);
        hairColorTextView = root.findViewById(R.id.hairColorTextView);
        eyeColorTextView = root.findViewById(R.id.eyeColorTextView);
        birthYearTextView = root.findViewById(R.id.birthYearTextView);
        genderTextView = root.findViewById(R.id.genderTextView);
        winnerTextView = root.findViewById(R.id.winner);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mFighter = (FighterEntity) getArguments().getSerializable(FIGHTER);
            showWinner();
            fighterNameTextView.setText(mFighter.getName());
            heightTextView.setText(mFighter.getHeight());
            massTextView.setText(mFighter.getMass());
            hairColorTextView.setText(mFighter.getHairColor());
            eyeColorTextView.setText(mFighter.getEyeColor());
            birthYearTextView.setText(mFighter.getBirthYear());
            genderTextView.setText(mFighter.getGender());
        }
    }

    private void showWinner() {
        if (getArguments().getBoolean(IS_WINNER)) {
            winnerTextView.setVisibility(View.VISIBLE);
        } else {
            winnerTextView.setVisibility(View.INVISIBLE);
        }
    }
}
