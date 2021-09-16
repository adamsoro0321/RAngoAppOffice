package adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.app.Objets.HouseModel;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.myviewholder> {
    final Context context ;
    final ArrayList<HouseModel> list ;
  final LayoutInflater inflater ;
    public HouseAdapter(Context context, ArrayList<HouseModel> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context) ;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.maison_model ,parent ,false) ;
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
           final  HouseModel house = list.get(position) ;

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class myviewholder extends RecyclerView.ViewHolder {
        ImageView img ;
        TextView txt , description ;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
         //   img = (ImageView) itemView.findViewById(R.id.imageView2) ;
          //  txt = itemView.findViewById(R.id.textView2) ;
        }
    }
}
