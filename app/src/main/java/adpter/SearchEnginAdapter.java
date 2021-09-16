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
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.activity.HouseDetailActivity;
import com.example.rangoappoffice.app.Objets.HouseModel;

import java.util.List;

public class SearchEnginAdapter extends RecyclerView.Adapter<SearchEnginAdapter.MyViewHolder>{
    final List<HouseModel> houseModelList  ;
    final Context context ;

    public SearchEnginAdapter(List<HouseModel> houseModelList,Context context) {
        this.houseModelList = houseModelList;
        this.context = context ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maison_model, parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      rentingRow(holder,houseModelList.get(position));
    }

    private void rentingRow( MyViewHolder holder , HouseModel model){
        holder.text.setText(model.getAdresse());
        holder.description.setText(model.getDescription());
        holder.loyer.setText(model.getLoyer()+ " FCFA");

        if (model.getCategori()!=null){
            holder.categori.setText(model.getCategori());
        }
        Glide.with(context).load(model.getImage())
                .placeholder(R.drawable.image)
                .into(holder.img) ;

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context , HouseDetailActivity.class) ;
            intent.putExtra("house",model) ;
            intent.putExtra("idHouse" , model.getIdHouse()) ;
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return houseModelList.size() ;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView text;
        final TextView description;
        final TextView loyer;
        final TextView categori;
        final ImageView img ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categori = itemView.findViewById(R.id.text_categotori);
            text = itemView.findViewById(R.id.text_adress_frag) ;
            description =itemView.findViewById(R.id.text_describ_frag);
            img =itemView.findViewById(R.id.img_frag) ;
            loyer = itemView.findViewById(R.id.text_loyer_frag);
        }
    }
}
