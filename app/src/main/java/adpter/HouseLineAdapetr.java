package adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import  com.example.rangoappoffice.R ;
import com.example.rangoappoffice.app.Objets.houseComposant;

import java.util.ArrayList;

public class HouseLineAdapetr extends RecyclerView.Adapter<HouseLineAdapetr.Myviewholder> {
      final Context context ;
      final ArrayList<houseComposant> list;

    public HouseLineAdapetr(  Context context , ArrayList<houseComposant> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.house_image , parent , false) ;
        return new Myviewholder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final houseComposant house =list.get(position);

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    static class Myviewholder extends RecyclerView.ViewHolder {
        final TextView text;
        final TextView description;
        final ImageView img ;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text_adress_frag);
            description = itemView.findViewById(R.id.text_describ_frag);
            img = itemView.findViewById(R.id.img_frag);


        }
    }
}
