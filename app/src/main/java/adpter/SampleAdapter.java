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
import com.example.rangoappoffice.model.People;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SampleAdapter extends FirestoreRecyclerAdapter<People, SampleAdapter.composantViewHolder> {
  private final Context context ;

    public SampleAdapter(@NonNull FirestoreRecyclerOptions<People> options , Context context ) {
        super(options);
        this.context = context ;

    }

    @Override
    protected void onBindViewHolder(@NonNull composantViewHolder holder, int position, @NonNull People model) {
        Glide.with(context).load(model.getImgUrl())
                .placeholder(R.drawable.image)
                .into(holder.img) ;

            designShow(holder ,model);

 }

    private  void designShow(composantViewHolder holder, People model){
        holder.name.setText(model.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent i =new Intent(context , LocataireChatWithGuide.class) ;
            i.putExtra("guideUId",model.getUid()) ;
            context.startActivity(i);

        });
    }

    @NonNull
    @Override
    public composantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_in_row , parent , false) ;
     return new composantViewHolder(view);
    }

  //beguin of holder
    public static class composantViewHolder extends RecyclerView.ViewHolder {
     final ImageView img ;
     final TextView name  ;
        public composantViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.text_name);
            img = itemView.findViewById(R.id.coch_img) ;

        }

    }
}
