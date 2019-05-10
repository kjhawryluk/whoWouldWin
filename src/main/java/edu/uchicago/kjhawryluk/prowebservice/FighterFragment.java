package edu.uchicago.kjhawryluk.prowebservice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FighterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FighterFragment extends Fragment {
    private static final String FIGHTER_ID = "fighterId";

    private int fighterId;
    @BindView(R.id.fighterNameTextView)
    private TextView fighterNameTextView;
    @BindView(R.id.heightTextView)
    private TextView heightTextView;
    @BindView(R.id.massTextView)
    private TextView massTextView;
    @BindView(R.id.hairColorTextView)
    private TextView hairColorTextView;
    @BindView(R.id.eyeColorTextView)
    private TextView eyeColorTextView;
    @BindView(R.id.birthYearTextView)
    private TextView birthYearTextView;
    @BindView(R.id.genderTextView)
    private TextView genderTextView;


    public FighterFragment() {
    }


    public static FighterFragment newInstance(int fighterId) {
        FighterFragment fragment = new FighterFragment();
        Bundle args = new Bundle();
        args.putInt(FIGHTER_ID, fighterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fighterId = getArguments().getInt(FIGHTER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fighter, container, false);
        fighterId = getArguments().getInt(FIGHTER_ID);
        fighterNameTextView.setText(String.valueOf(fighterId));
        return root;
    }

}
