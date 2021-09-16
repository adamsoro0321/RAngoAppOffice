package adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rangoappoffice.R;

import java.util.ArrayList;

public class spinerHouseAdapter extends ArrayAdapter<String> {

    public spinerHouseAdapter(@NonNull Context context, ArrayList<String> textViewResourceId) {
        super(context, 0, textViewResourceId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       if ( convertView == null){
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.spiner_house_layou ,parent ,false);
       }
       String item = getItem(position);
        TextView text = convertView.findViewById(R.id.spiner_house_text);
        if (item!= null){
            text.setText(item);
        }
        return convertView ;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if ( convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spiner_house_dropdow_layou ,parent ,false);
        }
        String item = getItem(position);
        TextView text_drop = convertView.findViewById(R.id.spiner_house_text_drop);
        if (item!= null){
            text_drop.setText(item);
        }
        return convertView ;
    }

}
