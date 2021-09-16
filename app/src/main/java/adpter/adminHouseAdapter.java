package adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.activity.HouseAdminActivity;
import com.example.rangoappoffice.activity.HouseDetailActivity;
import com.example.rangoappoffice.app.Objets.HouseModel;

import com.example.rangoappoffice.model.OwnerDialog;
import com.example.rangoappoffice.model.ownerDialogRetrieve;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class adminHouseAdapter extends FirestoreRecyclerAdapter<HouseModel,adminHouseAdapter.viewholder> {
    private final Context context ;
    final int DIALOD_SETTING ;
    public adminHouseAdapter(@NonNull FirestoreRecyclerOptions<HouseModel> options,int i,Context context) {
        super(options);
        this.context=context ;
        this.DIALOD_SETTING = i ;
    }

    @Override
    protected void onBindViewHolder(@NonNull adminHouseAdapter.viewholder holder, int position, @NonNull HouseModel model) {
          holder.inflate(model);
          holder.img.setOnClickListener(v -> {
              Intent intent = new Intent(context , HouseDetailActivity.class) ;
                      intent.putExtra("house" ,model) ;
                      context.startActivity(intent);
          });
          holder.menu_item.setOnClickListener(v -> {
              if (DIALOD_SETTING==HouseAdminActivity.SHOW_ALL_HOUSE){
                  showDialo(model);
              }
              if (DIALOD_SETTING==HouseAdminActivity.SHOW_TO_SET){
                  showSetDialog(model);
              }
          });
  }
    private void  showDialo(HouseModel model){
        FragmentActivity activity =(FragmentActivity)context ;
        FragmentManager fm =activity.getSupportFragmentManager() ;
        DialogFragment fragment = new OwnerDialog(context,model) ;
        fragment.show(fm ,"Dialog");
    }
  private  void showSetDialog(HouseModel model){
      FragmentActivity activity =(FragmentActivity)context ;
      FragmentManager fm =activity.getSupportFragmentManager() ;
      DialogFragment fragment = new ownerDialogRetrieve(context,model) ;
      fragment.show(fm ,"Dialog");
  }
    @NonNull
    @Override
    public adminHouseAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maison_model_admin ,parent,false);
        return new viewholder(view) ;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        final TextView categori;
        final TextView lieu;
        final TextView loyer ;
        final ImageView img;
        final ImageView menu_item;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            categori = itemView.findViewById(R.id.text_describ_frag) ;
            lieu =itemView.findViewById(R.id.text_adress_frag) ;
            loyer =itemView.findViewById(R.id.text_loyer_frag) ;
            img = itemView.findViewById(R.id.img_frag) ;
            menu_item =itemView.findViewById(R.id.menu_item) ;

        }
        private void inflate(HouseModel house){
            categori.setText(house.getCategori());
            lieu.setText(house.getAdresse());
            loyer.setText(house.getLoyer());
      if (house.getImage()!=null){
             Glide.with(context).load(house.getImage()).into(img) ;
      }
        }
    }
}
