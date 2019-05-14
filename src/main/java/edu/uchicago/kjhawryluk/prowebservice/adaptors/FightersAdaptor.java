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
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.FighterEntity;

/**
 * Modified from: https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
 */
public class FightersAdaptor extends ArrayAdapter<FighterEntity> {
    private List<FighterEntity> mPersonEntities;
    private Context mContext;

    public FightersAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public List<FighterEntity> getPersonEntities() {
        return mPersonEntities;
    }

    @Nullable
    @Override
    public FighterEntity getItem(int position) {
        return mPersonEntities.get(position);
    }


    @Override
    public int getPosition(@Nullable FighterEntity item) {
        return mPersonEntities.indexOf(item);
    }


    public int getPosition(@Nullable String item) {
        if (mPersonEntities != null) {
            for (int pos = 0; pos < mPersonEntities.size(); pos++) {
                if (mPersonEntities.get(pos).equals(item))
                    return pos;
            }
        }
        return 0;
    }

    public void setPersonEntities(List<FighterEntity> personEntities) {
        mPersonEntities = personEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.spinner_closed, parent, false);

        FighterEntity currentPerson = mPersonEntities.get(position);

        TextView spinnerText = (TextView) listItem.findViewById(R.id.spinnerText);
        spinnerText.setText(currentPerson.getName());
        return listItem;
    }

    @Override
    public int getCount() {
        if (mPersonEntities == null)
            return 0;
        return mPersonEntities.size();
    }
}
