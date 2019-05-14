package edu.uchicago.kjhawryluk.prowebservice.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;

@Dao
public interface PlanetDao {

    @Query("SELECT * FROM planets")
    LiveData<List<PlanetEntity>> loadPlanets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> savePlanets(List<PlanetEntity> planetEntities);

    @Query("SELECT * FROM planets WHERE url=:url")
    LiveData<PlanetEntity> getPlanet(String url);

}
