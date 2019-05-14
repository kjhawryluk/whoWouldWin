package edu.uchicago.kjhawryluk.prowebservice.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;
import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PlanetDao;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.FighterEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.typeconverters.ListConverter;

/**
 * Based on roomMovies by mertsimsek on 19/05/2017.
 */

@Database(entities = {FighterEntity.class, PlanetEntity.class}, version = 2)
@TypeConverters({ListConverter.class})
public abstract class StarWarsDatabase extends RoomDatabase {

    public abstract PeopleDao mPeopleDao();

    public abstract PlanetDao mPlanetDao();

    private static volatile StarWarsDatabase INSTANCE;

    public static StarWarsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StarWarsDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StarWarsDatabase.class, "star_wars_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static StarWarsDatabase.Callback sCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PeopleDao mPeopleDao;
        private final PlanetDao mPlanetDao;

        PopulateDbAsync(StarWarsDatabase db) {
            mPeopleDao = db.mPeopleDao();
            mPlanetDao = db.mPlanetDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Load json into database to have some offline data.
            return null;
        }
    }
}
