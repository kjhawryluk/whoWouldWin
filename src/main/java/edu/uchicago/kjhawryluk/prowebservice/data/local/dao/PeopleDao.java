package edu.uchicago.kjhawryluk.prowebservice.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;

@Dao
public interface PeopleDao {

    @Query("SELECT * FROM people")
    LiveData<List<PersonEntity>> loadPeople();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePeople(List<PersonEntity> peopleEntities);

    @Query("SELECT * FROM people WHERE name=:name")
    LiveData<PersonEntity> getPerson(String name);

}
