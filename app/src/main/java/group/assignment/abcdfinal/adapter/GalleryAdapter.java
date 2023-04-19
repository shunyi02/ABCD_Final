package group.assignment.abcdfinal.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import group.assignment.abcdfinal.R;
import group.assignment.abcdfinal.model.GalleryImages;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {

    private List<GalleryImages> list;

    SendImage onSendImage;

    public GalleryAdapter(List<GalleryImages> list){
        this.list = list;
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_items,parent,false);
        new GalleryHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryHolder holder, final int position) {

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(list.get(position).getPicUri())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(list.get(position).getPicUri());
            }
        });
    }

    private void chooseImage(Uri picUri){
        onSendImage.onSend(picUri);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class GalleryHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public GalleryHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public interface SendImage{
        void onSend(Uri picUri);
    }

    public void SendImage(SendImage sendImage){
        this.onSendImage = sendImage;
    }
}