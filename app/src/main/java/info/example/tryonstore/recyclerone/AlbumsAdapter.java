package info.example.tryonstore.recyclerone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import info.example.tryonstore.Product_Details_activity;
import info.example.tryonstore.R;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, Description, Prize;
        public ImageView Image;
        public LinearLayout opendetails;

        public MyViewHolder(View view) {
            super(view);
            Title = (TextView) view.findViewById(R.id.product_item_title);
            Description = (TextView) view.findViewById(R.id.product_item_description);
            Prize = (TextView) view.findViewById(R.id.product_item_price);
            Image = (ImageView) view.findViewById(R.id.product_item_pic);
            opendetails=(LinearLayout)view.findViewById(R.id.opendetails);


        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productlook, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Album album = albumList.get(position);
        holder.Title.setText(album.getTitle());
        holder.Description.setText(album.getDescription());
        holder.Prize.setText(album.getPrize());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getImage()).into(holder.Image);

        holder.opendetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(mContext.getApplicationContext(), Product_Details_activity.class);
                i.putExtra("title",album.getTitle());
                i.putExtra("description",album.getDescription());
                i.putExtra("prize",album.getPrize());
                i.putExtra("image",album.getImage());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
