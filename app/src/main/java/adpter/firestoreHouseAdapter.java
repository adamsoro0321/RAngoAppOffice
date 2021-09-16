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
import com.example.rangoappoffice.activity.CategoriSearchActivity;
import com.example.rangoappoffice.activity.HouseDetailActivity;
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.CommonMethode;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.fragment.SearchFragment;
import com.example.rangoappoffice.ui.home.HomeFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class firestoreHouseAdapter extends FirestoreRecyclerAdapter<HouseModel , firestoreHouseAdapter.HouseViewHolder> {


   final Context context ;
   private final int CONSTANT_COMING  ;

    public firestoreHouseAdapter(@NonNull FirestoreRecyclerOptions<HouseModel> options , Context context,int  i) {
        super(options);
     this.context =context ;
     this.CONSTANT_COMING =i ;
    }

    @Override
    protected void onBindViewHolder(@NonNull HouseViewHolder holder, int position, @NonNull HouseModel model) {
   if (CONSTANT_COMING == HomeFragment.RENTING_CONTANT){
       if (model!= null && model.getIdHouse()!=null){
           rentingRow(holder ,model);
       }else{
           holder.itemView.setVisibility(View.GONE);
       }
   }

    if (CONSTANT_COMING==CategoriSearchActivity.CONsTANT_CONFIG){
        if (model!= null && model.getIdHouse()!=null){
            rentingRow(holder ,model);
        }else{
            holder.itemView.setVisibility(View.GONE);
        }
    }
    if (CONSTANT_COMING == CategoriSearchActivity.CONsTANT_CONFIG){

    }if (CONSTANT_COMING == SearchFragment.CONSTANT_CONFIG){
            if (model!= null && model.getIdHouse()!=null){
                rentingRow(holder ,model);
            }else{
                holder.itemView.setVisibility(View.GONE);
            }
        }

    }

   private void rentingRow(HouseViewHolder holder ,HouseModel model){
       holder.text.setText(model.getAdresse());
       holder.description.setText(model.getDescription());
       holder.loyer.setText(CommonMethode.ConvertAnFormat(model.getLoyer())+" FCFA");
       if (model.getCategori()!=null){
           holder.categori.setText(model.getCategori());
       }
       Glide.with(context).load(model.getImage())
               .placeholder(R.drawable.image)
               .into(holder.img) ;

       holder.itemView.setOnClickListener(v -> {
           Intent intent = new Intent(context , HouseDetailActivity.class) ;
           intent.putExtra("house",model) ;
           intent.putExtra("CONSTANT_COMING" ,CONSTANT_COMING) ;
           context.startActivity(intent);


       });
   }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maison_model, parent , false);

        return new HouseViewHolder(view);
    }


    public static class HouseViewHolder extends RecyclerView.ViewHolder{
          final TextView text;
        final TextView description;
        final TextView loyer;
        final TextView categori;
          final ImageView img ;
       public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            categori = itemView.findViewById(R.id.text_categotori);
            text = itemView.findViewById(R.id.text_adress_frag) ;
            description =itemView.findViewById(R.id.text_describ_frag);
            img =itemView.findViewById(R.id.img_frag) ;
            loyer = itemView.findViewById(R.id.text_loyer_frag);
        }
    }
}
