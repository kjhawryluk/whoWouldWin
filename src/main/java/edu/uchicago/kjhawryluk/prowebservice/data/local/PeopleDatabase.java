package edu.uchicago.kjhawryluk.prowebservice.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import edu.uchicago.kjhawryluk.prowebservice.data.local.dao.PeopleDao;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;

/**
 * Based on roomMovies by mertsimsek on 19/05/2017.
 */
@Database(entities = {PersonEntity.class}, version = 2)
public abstract class PeopleDatabase extends RoomDatabase{

    public abstract PeopleDao mPeopleDao();
}
