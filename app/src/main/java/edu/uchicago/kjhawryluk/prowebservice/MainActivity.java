package edu.uchicago.kjhawryluk.prowebservice;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import edu.uchicago.kjhawryluk.prowebservice.data.StarWarsRepository;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.FighterEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;
import edu.uchicago.kjhawryluk.prowebservice.viewmodels.StarWarsViewModel;

public class MainActivity extends AppCompatActivity implements FightFragment.OnFightListener {
    private TextView mTextMessage;
    private FighterEntity fighter1;
    private FighterEntity fighter2;
    private PlanetEntity mPlanetEntity;
    private StarWarsViewModel mStarWarsViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FighterEntity fighterEntity;
            switch (item.getItemId()) {
                case R.id.fighter1Nav:
                    fighterEntity = fighter1;
                    if (fighterEntity != null) {
                        loadFighter(fighterEntity);
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
                    fighterEntity = fighter2;
                    if (fighterEntity != null) {
                        loadFighter(fighterEntity);
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


    void loadFighter(FighterEntity fighter) {
        FighterFragment fighterFragment = FighterFragment.newInstance(fighter);
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

    void launchWinner(FighterEntity winner) {
        FighterFragment fighterFragment = FighterFragment.newInstance(winner, true);
        swapInFragment(fighterFragment);
    }
    public void startFight() {
        FightTracker fightTracker = new FightTracker(fighter1, fighter2, mPlanetEntity);
        mStarWarsViewModel.startFight(this, fightTracker);

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
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mStarWarsViewModel = ViewModelProviders.of(this).get(StarWarsViewModel.class);
        if (savedInstanceState == null) {
            FighterEntity winner = (FighterEntity) getIntent().getSerializableExtra(StarWarsRepository.WINNER);
            if (winner != null) {
                launchWinner(winner);
            } else {
                loadFight();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!StarWarsRepository.compositeDisposable.isDisposed()) {
            StarWarsRepository.compositeDisposable.dispose();
        }
    }


    public FighterEntity getFighter1() {
        return fighter1;
    }

    public void setFighter1(FighterEntity fighter1) {
        this.fighter1 = fighter1;
    }

    public FighterEntity getFighter2() {
        return fighter2;
    }

    public void setFighter2(FighterEntity fighter2) {
        this.fighter2 = fighter2;
    }

    public PlanetEntity getPlanet() {
        return mPlanetEntity;
    }

    public void setPlanet(PlanetEntity planetEntity) {
        mPlanetEntity = planetEntity;
    }
}
