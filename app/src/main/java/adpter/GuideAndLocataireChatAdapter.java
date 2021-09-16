package adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.model.messageChat;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class  GuideAndLocataireChatAdapter extends FirestoreRecyclerAdapter<messageChat,
        GuideAndLocataireChatAdapter.MessageViewHolder> {
       final Context context ;
     private final String curentUser ;

    public GuideAndLocataireChatAdapter(@NonNull FirestoreRecyclerOptions<messageChat> options , String curentUser ,Context context) {
        super(options);
        this.curentUser = curentUser ;
        this.context = context ;
    }

    @Override
    protected void onBindViewHolder(@NonNull GuideAndLocataireChatAdapter.MessageViewHolder holder,
                                    int position, @NonNull messageChat model) {
                    design(holder ,model) ;
    }
   private void design(GuideAndLocataireChatAdapter.MessageViewHolder holder ,messageChat model){
        if (model.getUserUID().equals(curentUser)){
            holder.msgBody.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
        }
      if (model.getDateCreated()!=null){
          holder.date.setText(dateFomat(model));
      }
       holder.msgBody.setText(model.getMsg());
   }
   private String dateFomat(messageChat model){
        Date date = model.getDateCreated() ;
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm") ;
       return simpleDateFormat.format(date);
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_with_guide, parent, false);
        return new MessageViewHolder(view);
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        final TextView msgBody;
        final TextView date ;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            msgBody= itemView.findViewById(R.id.msg_body);
            date   = itemView.findViewById(R.id.date_contain);
        }
    }
}
