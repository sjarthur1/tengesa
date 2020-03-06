package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mobile.tengesa.R;
import com.objects.list_objects.Country;

import java.util.List;

public class CountriesListAdapter extends BaseAdapter {
    private final Context context;
    private final List<Country> values;

    public CountriesListAdapter(Context context, List<Country> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Country countryItem = values.get( position );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate( R.layout.country_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.txtViewCountryName);

        String country = countryItem.getCode()+" "+countryItem.getCountry();
        textView.setText( country );

        return rowView;
    }

    private String GetCountryZipCode(String country){
        int firstLetter = Character.codePointAt( country, 0 ) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt( country, 1 ) - 0x41 + 0x1F1E6;
        return new String( Character.toChars( firstLetter ) ) + new String( Character.toChars( secondLetter ) );
    }
}
