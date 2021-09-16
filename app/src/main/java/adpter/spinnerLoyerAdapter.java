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


public class spinnerLoyerAdapter extends  ArrayAdapter<String> {

    public spinnerLoyerAdapter(@NonNull Context context, ArrayList<String> resource) {
        super(context,0,resource );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if ( convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spiner_loyer_layou ,parent ,false);
        }
         String item = getItem(position);
        TextView loyer_tex = convertView.findViewById(R.id.loyer_text);

        if (item!= null){
            loyer_tex.setText(item + "F CFA");
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if ( convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spiner_loyer_dropdown ,parent ,false);
        }

         String itemDrop = getItem(position);
        TextView loyer_drop =convertView.findViewById(R.id.loyer_text_drop);

        if (itemDrop != null){
            loyer_drop.setText(itemDrop + "F CFA");
        }
        return convertView ;
    }
}
