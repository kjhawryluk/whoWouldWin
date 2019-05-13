package edu.uchicago.kjhawryluk.prowebservice;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FightFragment.OnFightListener {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String fighterName;
            switch (item.getItemId()) {
                case R.id.fighter1Nav:
                    fighterName = getFighterName(R.id.fighter1Spinner);
                    if (fighterName != null)
                        loadFighter(fighterName);
                    return true;
                case R.id.fightNav:
                    loadFight();
                    return true;
                case R.id.fighter2Nav:
                    fighterName = getFighterName(R.id.fighter2Spinner);
                    if (fighterName != null)
                        loadFighter(fighterName);
                    return true;
            }
            return false;
        }
    };

    String getFighterName(int spinnerId) {
        Spinner spinner = findViewById(spinnerId);
        if (spinner != null && spinner.getCount() > 0) {
            return spinner.getSelectedItem().toString();
        }
        return null;
    }


    void loadFighter(String fighterName) {
        FighterFragment fighterFragment = FighterFragment.newInstance(fighterName);
        swapInFragment(fighterFragment);
    }

    void loadFight(){
        FightFragment fightFragment = new FightFragment();
        swapInFragment(fightFragment);
    }


    void swapInFragment(Fragment fragment){
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setSelectedItemId(R.id.fightNav);
        loadFight();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void onFightFragmentInteraction(){
    }

    @Override
    public void onSpinner1Interaction() {
        Spinner fighter1Spinner = findViewById(R.id.fighter1Spinner);
        if (fighter1Spinner != null) {
            Log.i("FIGHTER", "AHHH");
        }
    }

}
