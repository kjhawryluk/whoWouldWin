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

import edu.uchicago.kjhawryluk.prowebservice.data.StarWarsRepository;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;

public class MainActivity extends AppCompatActivity implements FightFragment.OnFightListener {
    private TextView mTextMessage;
    private PersonEntity fighter1;
    private PersonEntity fighter2;
    private PlanetEntity mPlanetEntity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String fighterName;
            switch (item.getItemId()) {
                case R.id.fighter1Nav:
                    fighterName = fighter1.getName();
                    if (fighterName != null) {
                        loadFighter(fighterName);
                        return true;
                    }
                    return false;

                case R.id.fightNav:
                    if (fighter1 == null || fighter2 == null) {
                        initFight();
                    } else {
                        loadFight();
                    }
                    return true;
                case R.id.fighter2Nav:
                    fighterName = fighter2.getName();
                    if (fighterName != null) {
                        loadFighter(fighterName);
                        return true;
                    }
                    return false;
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

    void initFight() {
        FightFragment fightFragment = FightFragment.newInstance();
        swapInFragment(fightFragment);
    }

    void loadFight() {
        FightFragment fightFragment = FightFragment.newInstance(fighter1, fighter2, mPlanetEntity);
        swapInFragment(fightFragment);
    }

    void swapInFragment(Fragment fragment) {
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


    public void onFightFragmentInteraction() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!StarWarsRepository.compositeDisposable.isDisposed()) {
            StarWarsRepository.compositeDisposable.dispose();
        }
    }


    public PersonEntity getFighter1() {
        return fighter1;
    }

    public void setFighter1(PersonEntity fighter1) {
        this.fighter1 = fighter1;
    }

    public PersonEntity getFighter2() {
        return fighter2;
    }

    public void setFighter2(PersonEntity fighter2) {
        this.fighter2 = fighter2;
    }

    public PlanetEntity getPlanet() {
        return mPlanetEntity;
    }

    public void setPlanet(PlanetEntity planetEntity) {
        mPlanetEntity = planetEntity;
    }
}
