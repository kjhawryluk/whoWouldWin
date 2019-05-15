package edu.uchicago.kjhawryluk.prowebservice.adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.uchicago.kjhawryluk.prowebservice.R;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PlanetEntity;

/**
 * Modified from: https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
 */
public class PlanetsAdaptor extends ArrayAdapter<PlanetEntity> {
    private List<PlanetEntity> mPlanetEntities;
    private Context mContext;

    public PlanetsAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public List<PlanetEntity> getPlanetEntities() {
        return mPlanetEntities;
    }

    @Nullable
    @Override
    public PlanetEntity getItem(int position) {
        return mPlanetEntities.get(position);
    }


    @Override
    public int getPosition(@Nullable PlanetEntity item) {
        return mPlanetEntities.indexOf(item);
    }


    public int getPosition(@Nullable String item) {
        for (int pos = 0; pos < mPlanetEntities.size(); pos++) {
            if (mPlanetEntities.get(pos).equals(item))
                return pos;
        }
        return 0;
    }

    public void setPlanetEntities(List<PlanetEntity> planetEntities) {
        mPlanetEntities = planetEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.spinner_closed, parent, false);

        PlanetEntity currentPlanet = mPlanetEntities.get(position);

        TextView spinnerText = (TextView) listItem.findViewById(R.id.spinnerText);
        spinnerText.setText(currentPlanet.getName());
        return listItem;
    }

    @Override
    public int getCount() {
        if (mPlanetEntities == null)
            return 0;
        return mPlanetEntities.size();
    }
}
