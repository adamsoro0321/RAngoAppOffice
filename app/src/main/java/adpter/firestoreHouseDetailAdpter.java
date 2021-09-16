package adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.app.Objets.houseComposant;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class firestoreHouseDetailAdpter extends FirestoreRecyclerAdapter<houseComposant, firestoreHouseDetailAdpter.composantViewHolder> {
    private final Context context ;
    public firestoreHouseDetailAdpter(@NonNull FirestoreRecyclerOptions<houseComposant> options ,Context context) {
        super(options);
        this.context = context ;
       }

    @Override
    protected void onBindViewHolder(@NonNull composantViewHolder holder, int position, @NonNull houseComposant model) {
        if (model.getUri()!=null){
            Glide.with(context).load(model.getUri())
                    .placeholder(R.drawable.image)
                    .into(holder.img) ;
        }else{
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public composantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.house_image , parent , false) ;
        return new composantViewHolder(view);
    }

    public static class composantViewHolder extends RecyclerView.ViewHolder {
                 final ImageView img ;
        public composantViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.house_d_img);

        }
    }
}
