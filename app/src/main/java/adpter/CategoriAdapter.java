package adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.activity.CategoriSearchActivity;

import com.example.rangoappoffice.activity.MainActivity;
import com.example.rangoappoffice.model.CategoriHome;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CategoriAdapter extends FirestoreRecyclerAdapter<CategoriHome, CategoriAdapter.composantViewHolder> {
  private final Context context ;

    public CategoriAdapter(@NonNull FirestoreRecyclerOptions<CategoriHome> options , Context context) {
        super(options);
        this.context = context ;
    }

    @Override
    protected void onBindViewHolder(@NonNull composantViewHolder holder, int position, @NonNull CategoriHome model) {

        holder.description.setText(model.getcategori());


        holder.itemView.setOnClickListener(v -> {

                Intent intent = new Intent(context , MainActivity.class);
                intent.putExtra("query_searching" ,model.getcategori());
                context.startActivity(intent);
                 });
    }

    @NonNull
    @Override
    public composantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categori_home , parent , false) ;
        return new composantViewHolder(view);
    }

    public static class composantViewHolder extends RecyclerView.ViewHolder {

   final TextView description ;
        public composantViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description_categori);
        }
    }
}
