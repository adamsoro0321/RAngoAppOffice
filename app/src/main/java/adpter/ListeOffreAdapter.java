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
import com.example.rangoappoffice.activity.LocataireChatWithGuide;
import com.example.rangoappoffice.activity.MainActivity;
import com.example.rangoappoffice.api.CommonMethode;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.model.ChooseGuide;
import com.example.rangoappoffice.model.LocataireInfo;
import com.example.rangoappoffice.model.OffreGuiddeModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ListeOffreAdapter extends FirestoreRecyclerAdapter<OffreGuiddeModel, ListeOffreAdapter.ViewHolder> {
 final Context context ;
private final LocataireInfo locataireInfo ;

    public ListeOffreAdapter(@NonNull FirestoreRecyclerOptions<OffreGuiddeModel> options, LocataireInfo locataireInfo, Context context) {
        super(options);
        this.context =context ;
       this.locataireInfo =locataireInfo ;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OffreGuiddeModel model) {
             holder.inflate(model);
        holder.itemView.setOnClickListener(v ->{
                 goTochat() ;
                 AlertGuide(model.getUid());
             } );
 }
 private  void AlertGuide(String guide){
   ChooseGuide choose = new ChooseGuide("guide",MainActivity.currentUser);
     UserHelper.ChooseListener(guide).document(MainActivity.currentUser).set(choose) ;
 }
    private  void goTochat(){
        Intent i = new Intent(context , LocataireChatWithGuide.class);
       i.putExtra("locataireInfo",locataireInfo) ;
       context.startActivity(i);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offre_layou , parent , false) ;
        return new ViewHolder(view) ;
   }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tarif;
        final TextView condition ;
        final ImageView img ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tarif = itemView.findViewById(R.id.tarif_offre) ;
            condition = itemView.findViewById(R.id.cond_offre) ;
            img =itemView.findViewById(R.id.offre_img) ;
        }
        private  void inflate(OffreGuiddeModel offre){
            tarif.setText(CommonMethode.ConvertAnFormat(offre.getTarif())+"F CFA" );
            condition.setText(offre.getCondition());
            if (offre.getImg()!=null){
                Glide.with(context).load(offre.getImg()).into(img);
            }

        }
    }
}
