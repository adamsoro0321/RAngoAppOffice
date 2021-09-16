package adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rangoappoffice.activity.HouseDetailActivity;
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.CommonMethode;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class houseDetailLineAdapter extends FirestoreRecyclerAdapter<HouseModel, houseDetailLineAdapter.composantViewHolder> {
  private final Context context ;

    public houseDetailLineAdapter(@NonNull FirestoreRecyclerOptions<HouseModel> options , Context context) {
        super(options);
        this.context = context ;
    }

    @Override
    protected void onBindViewHolder(@NonNull composantViewHolder holder, int position, @NonNull HouseModel model) {
        if (!model.getLoyer().isEmpty() && model.getLoyer()!=null ){
            holder.textLine.setText(CommonMethode.ConvertAnFormat(model.getLoyer()) + " FCFA ");
        }
        if (!model.getDescription().isEmpty() && model.getDescription() !=null){
            holder.description.setText(model.getDescription());
        }
        if (!model.getCategori().isEmpty() && model.getCategori() !=null){
            holder.categori.setText(model.getCategori());
        }
        if (!model.getAdresse().isEmpty() && model.getAdresse()!=null){
            holder.adress.setText( model.getAdresse());
        }
        Glide.with(context).load(model.getImage())
                .placeholder(R.drawable.home)
                .into(holder.imgLine) ;

        holder.itemView.setOnClickListener(v -> {
                 Intent intent = new Intent(context , HouseDetailActivity.class);

                 intent.putExtra("idHouse" , model.getIdHouse());
                 context.startActivity(intent);


        });
    }

    @NonNull
    @Override
    public composantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.house_model_ , parent , false) ;
        return new composantViewHolder(view);
    }

    public static class composantViewHolder extends RecyclerView.ViewHolder {
    final TextView textLine;
        final TextView description;
        final TextView adress;
        final TextView categori;
    final ImageView imgLine ;
        public composantViewHolder(@NonNull View itemView) {
            super(itemView);
            textLine = itemView.findViewById(R.id.text_line);
            imgLine = itemView.findViewById(R.id.img_line) ;
            description = itemView.findViewById(R.id.text_desciption);
            adress = itemView.findViewById(R.id.text_adress) ;
            categori = itemView.findViewById(R.id.text_categotori);

        }
    }
}
