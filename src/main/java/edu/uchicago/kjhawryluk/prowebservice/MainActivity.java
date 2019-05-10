package edu.uchicago.kjhawryluk.prowebservice;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FightFragment.OnFightListener {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.fighter1Nav:
                    loadFighter(1);
                    return true;
                case R.id.fightNav:
                    loadFight();
                    return true;
                case R.id.fighter2Nav:
                    loadFighter(100);
                    return true;
            }
            return false;
        }
    };

    void loadFighter(int id){
        FighterFragment fighterFragment = FighterFragment.newInstance(id);
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
}
