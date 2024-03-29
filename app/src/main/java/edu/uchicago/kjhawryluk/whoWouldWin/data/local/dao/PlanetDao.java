package edu.uchicago.kjhawryluk.whoWouldWin.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.whoWouldWin.data.local.entity.PlanetEntity;

@Dao
public interface PlanetDao {

    @Query("SELECT * FROM planets")
    LiveData<List<PlanetEntity>> loadPlanets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> savePlanets(List<PlanetEntity> planetEntities);

    @Query("SELECT * FROM planets WHERE url=:url ORDER BY name")
    PlanetEntity getPlanetByUrl(String url);

    @Query("SELECT * FROM planets WHERE url=:name LIMIT 1")
    PlanetEntity getPlanetByName(String name);
}
