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
    private static final String FIGHTER_NAME = "fighterName";

    private String mFighterName;
    TextView fighterNameTextView;
    @BindView(R.id.heightTextView)
    TextView heightTextView;
    @BindView(R.id.massTextView)
    TextView massTextView;
    @BindView(R.id.hairColorTextView)
    TextView hairColorTextView;
    @BindView(R.id.eyeColorTextView)
    TextView eyeColorTextView;
    @BindView(R.id.birthYearTextView)
    TextView birthYearTextView;
    @BindView(R.id.genderTextView)
    TextView genderTextView;


    public FighterFragment() {
    }


    public static FighterFragment newInstance(String fighterName) {
        FighterFragment fragment = new FighterFragment();
        Bundle args = new Bundle();
        args.putString(FIGHTER_NAME, fighterName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFighterName = getArguments().getString(FIGHTER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fighter, container, false);
        mFighterName = getArguments().getString(FIGHTER_NAME);
        fighterNameTextView = root.findViewById(R.id.fighterNameTextView);
        fighterNameTextView.setText(mFighterName);
        return root;
    }

}
