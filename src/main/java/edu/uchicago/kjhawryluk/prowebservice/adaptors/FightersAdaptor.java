package edu.uchicago.kjhawryluk.prowebservice.adaptors;

import android.arch.lifecycle.LiveData;
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
import edu.uchicago.kjhawryluk.prowebservice.data.Resource;
import edu.uchicago.kjhawryluk.prowebservice.data.local.entity.PersonEntity;

/**
 * Modified from: https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
 */
public class FightersAdaptor extends ArrayAdapter<PersonEntity> {
    private Resource<List<PersonEntity>> mPersonEntities;
    private Context mContext;

    public FightersAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public Resource<List<PersonEntity>> getPersonEntities() {
        return mPersonEntities;
    }

    public void setPersonEntities(Resource<List<PersonEntity>> personEntities) {
        mPersonEntities = personEntities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.spinner_closed, parent, false);

        PersonEntity currentPerson = mPersonEntities.data.get(position);

        TextView spinnerText = (TextView) listItem.findViewById(R.id.spinnerText);
        spinnerText.setText(currentPerson.getName());
        return listItem;
    }


}