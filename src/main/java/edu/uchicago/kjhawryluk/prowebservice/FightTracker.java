package edu.uchicago.kjhawryluk.prowebservice;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.FighterEntity;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;

public class FightTracker {
    private FighterEntity mFighter1;
    private FighterEntity mFighter2;
    private PlanetEntity mHostPlanet;
    private PlanetEntity mPlanet1;
    private PlanetEntity mPlanet2;
    private int mFighter1Score;
    private int mFighter2Score;

    public FightTracker(FighterEntity fighter1, FighterEntity fighter2, PlanetEntity hostPlanet, PlanetEntity planet1, PlanetEntity planet2) {
        mFighter1 = fighter1;
        mFighter2 = fighter2;
        mHostPlanet = hostPlanet;
        mPlanet1 = planet1;
        mPlanet2 = planet2;
        mFighter1Score = 0;
        mFighter2Score = 0;
    }

    void calculateScore() {
        setMassPoints();
        setHeightPoints();
        setAgePoints();
        setPlotArmourPoints();
        setVehiclePoints();
        setStarshipPoints();
        setPlanetPoints();
    }

    void setMassPoints() {
        try {
            int fighter1Mass = Integer.parseInt(mFighter1.getMass());
            int fighter2Mass = Integer.parseInt(mFighter2.getMass());
            int weightDifference = fighter1Mass - fighter2Mass;
            int proratedPoints = weightDifference / 10;
            if (fighter1Mass > fighter2Mass) {
                mFighter1Score += proratedPoints;
            } else {
                mFighter2Score += proratedPoints;
            }
        } catch (NumberFormatException e) {
            Log.i("Fight Scoring", "Unable to parse mass. " + e.getMessage());
        }
    }

    void setHeightPoints() {
        try {
            int fighter1Height = Integer.parseInt(mFighter1.getHeight());
            int fighter2Height = Integer.parseInt(mFighter2.getHeight());
            int weightDifference = fighter1Height - fighter2Height;
            int proratedPoints = (int) Math.round(Math.sqrt(weightDifference));
            if (fighter1Height > fighter2Height) {
                mFighter1Score += proratedPoints;
            } else {
                mFighter2Score += proratedPoints;
            }
        } catch (NumberFormatException e) {
            Log.i("Fight Scoring", "Unable to parse height. " + e.getMessage());
        }
    }

    void setAgePoints() {
        try {
            int fighter1BirthYear = getBirthYear(mFighter1.getBirthYear());
            int fighter2BirthYear = getBirthYear(mFighter2.getBirthYear());
            int ageDifference = fighter1BirthYear - fighter2BirthYear;
            int proratedPoints = (int) Math.round(Math.sqrt(ageDifference));
            if (fighter1BirthYear > fighter2BirthYear) {
                mFighter1Score += proratedPoints;
            } else {
                mFighter2Score += proratedPoints;
            }
        } catch (NumberFormatException e) {
            Log.i("Fight Scoring", "Unable to parse age. " + e.getMessage());
        }
    }

    int getBirthYear(String birthYear) {
        if (birthYear.contains("BBY")) {
            birthYear = birthYear.replace("BBY", "");
            return (-1) * Integer.parseInt(birthYear);
        } else {
            birthYear = birthYear.replace("ABY", "");
            return Integer.parseInt(birthYear);
        }
    }

    /**
     * This gives a bonus for being in more films because they're less likely to die.
     */
    void setPlotArmourPoints() {
        mFighter1Score += mFighter1.getFilms().size() * 3;
        mFighter2Score += mFighter2.getFilms().size() * 3;
    }

    void setVehiclePoints() {
        mFighter1Score += mFighter1.getVehicles().size();
        mFighter2Score += mFighter2.getVehicles().size();
    }

    void setStarshipPoints() {
        mFighter1Score += mFighter1.getStarships().size();
        mFighter2Score += mFighter2.getStarships().size();
    }

    void setPlanetPoints() {
        mFighter1Score += getPlanetPoints(mPlanet1);
        mFighter2Score += getPlanetPoints(mPlanet2);
    }

    int getPlanetPoints(PlanetEntity planet) {
        if (mHostPlanet.equals(planet))
            return 10;

        int points = 0;

        if (parseDifference(mHostPlanet.getRotationPeriod(), planet.getRotationPeriod()) < 2)
            points += 1;

        if (parseDifference(mHostPlanet.getRotationPeriod(), planet.getRotationPeriod()) < 25)
            points += 1;

        try {
            int hostDiam = Integer.parseInt(mHostPlanet.getDiameter());
            if (Math.abs(parseDifference(mHostPlanet.getDiameter(), planet.getDiameter())) < .25 * hostDiam)
                points += 1;
        } catch (NumberFormatException e) {
            Log.i("Fight Scoring", "Unable to parse host diam. " + e.getMessage());
        }

        if (percentInCommon(mHostPlanet.getClimate(), planet.getClimate()) > .5)
            points += 1;

        if (mHostPlanet.getGravity().equals(planet.getGravity()))
            points += 1;

        if (percentInCommon(mHostPlanet.getTerrain(), planet.getTerrain()) > .5)
            points += 1;

        return points;
    }

    private int parseDifference(String metric1, String metric2) {
        try {
            return Integer.parseInt(metric1) - Integer.parseInt(metric2);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double percentInCommon(String hostString, String fighterString) {
        if (hostString == null || fighterString == null)
            return 0;

        String[] hostArray = hostString.split(",");
        String[] fighterArray = fighterString.split(",");

        // Convert to List to use contains.
        List<String> hostList = Arrays.asList(hostArray);
        List<String> fighterList = Arrays.asList(fighterArray);

        double hostSize = new Double(hostList.size());

        // Nothing in common
        if (hostSize == 0)
            return 0;

        int elemInCommon = 0;

        // Count the number of elems they have in common.
        for (String item : hostList) {
            if (fighterList.contains(item))
                elemInCommon += 1;
        }
        return new Double(elemInCommon) / hostSize;
    }
}