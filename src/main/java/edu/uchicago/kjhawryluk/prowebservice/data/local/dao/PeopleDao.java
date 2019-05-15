package edu.uchicago.kjhawryluk.prowebservice.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.FighterEntity;

@Dao
public interface PeopleDao {

    @Query("SELECT * FROM people ORDER BY name")
    LiveData<List<FighterEntity>> loadPeople();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> savePeople(List<FighterEntity> peopleEntities);

    @Query("SELECT * FROM people WHERE name=:name")
    FighterEntity getPerson(String name);

}
