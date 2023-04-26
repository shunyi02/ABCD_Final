package group.assignment.abcdfinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import group.assignment.abcdfinal.R;
import group.assignment.abcdfinal.model.Users;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private List<Users> list;

    OnUserClicked onUserClicked;

    public UserAdapter(List<Users> list) {
        this.list = list;
    }

    @NotNull
    @Override
    public UserHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_items, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull UserHolder holder, int position) {

        if (list.get(position).getUid().equals(user.getUid())){
            holder.layout.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else{
            holder.layout.setVisibility(View.VISIBLE);
        }

        holder.nameTV.setText(list.get(position).getName());
        holder.statusTV.setText(list.get(position).getStatus());

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_person)
                .timeout(6500)
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClicked.onClicked(list.get(position).getUid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void OnUserClicked (OnUserClicked onUserClicked){
        this.onUserClicked = onUserClicked;
    }

    public interface OnUserClicked{
        void onClicked(String uid);
    }
    static class UserHolder extends RecyclerView.ViewHolder{
        private CircleImageView profileImage;
        private TextView nameTV, statusTV;
        private RelativeLayout layout;

        public UserHolder(@NotNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profileImage);
            nameTV = itemView.findViewById(R.id.nameTV);
            statusTV = itemView.findViewById(R.id.statusTV);
            layout = itemView.findViewById(R.id.relativeLayout);
        }

    }



}