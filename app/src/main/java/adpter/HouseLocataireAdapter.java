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
import com.example.rangoappoffice.activity.ChatBoxActivity;
import com.example.rangoappoffice.activity.MainActivity;
import com.example.rangoappoffice.model.HouseLocataire;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class HouseLocataireAdapter extends FirestoreRecyclerAdapter<HouseLocataire,HouseLocataireAdapter.viewholder> {
    private final Context context ;


    public HouseLocataireAdapter(@NonNull FirestoreRecyclerOptions<HouseLocataire> options,Context context) {
        super(options);
        this.context = context ;
    }


    @Override
    protected void onBindViewHolder(@NonNull HouseLocataireAdapter.viewholder holder, int position, @NonNull HouseLocataire model) {
     holder.inflate(model);
     holder.chatWhitLoca.setOnClickListener(v -> gotoChat());
    }
  private void gotoChat(){
  Intent i =new Intent(context , ChatBoxActivity.class) ;
  i.putExtra("ownerUId" , MainActivity.currentUser) ;
  context.startActivity(i);
  }
    @NonNull
    @Override
    public HouseLocataireAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_locataire_layout ,parent ,false) ;

        return new viewholder(view) ;
    }


    public  class viewholder extends RecyclerView.ViewHolder {

        final TextView categori;
        final TextView lieuH;
        final TextView locatName;
        final ImageView imgHouse;
        final ImageView chatWhitLoca ;
        final CircleImageView imgLoca;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            categori =itemView.findViewById(R.id.categorie);
            lieuH=itemView.findViewById(R.id.lieu_h);
            locatName=itemView.findViewById(R.id.locat_name);

            imgLoca= itemView.findViewById(R.id.img_loca);
            imgHouse= itemView.findViewById(R.id.img_house);
           chatWhitLoca = itemView.findViewById(R.id.chat_whit_loca) ;

        }
        private void inflate(HouseLocataire locataire){
            categori.setText(locataire.getHouseCategori());
            Glide.with(context).load(locataire.getLocataireImg()).into(imgLoca) ;
            Glide.with(context).load(locataire.getHouseImg()).into(imgHouse) ;
            lieuH.setText(locataire.getHouseLieu());
            locatName.setText(locataire.getLocataireName());

        }
    }
}
