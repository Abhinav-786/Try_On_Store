package info.example.tryonstore.recyclertwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import info.example.tryonstore.R;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdaptertwo extends RecyclerView.Adapter<AlbumsAdaptertwo.MyViewHolder> {

    private Context mContext;
    private List<Albumtwo> albumtwoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, Description,Prize;
        public ImageView Image;

        public MyViewHolder(View view) {
            super(view);
            Title = (TextView) view.findViewById(R.id.product_item_title);
            Description = (TextView) view.findViewById(R.id.product_item_description);
            Prize = (TextView) view.findViewById(R.id.product_item_price);
            Image = (ImageView) view.findViewById(R.id.product_item_pic);

        }
    }


    public AlbumsAdaptertwo(Context mContext, List<Albumtwo> albumtwoList) {
        this.mContext = mContext;
        this.albumtwoList = albumtwoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productlook, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Albumtwo albumtwo = albumtwoList.get(position);
        holder.Title.setText(albumtwo.getTitle());
        holder.Description.setText(albumtwo.getDescription());
        holder.Prize.setText(albumtwo.getPrize());

        // loading album cover using Glide library
        Glide.with(mContext).load(albumtwo.getImage()).into(holder.Image);


    }



    @Override
    public int getItemCount() {
        return albumtwoList.size();
    }
}
