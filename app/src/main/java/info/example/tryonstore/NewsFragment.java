package info.example.tryonstore;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import info.example.tryonstore.recyclerone.Album;
import info.example.tryonstore.recyclerone.AlbumsAdapter;

public class NewsFragment extends Fragment {
    RecyclerView home_best_sellings, home_trending, home_tv;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    private AlbumsAdapter adapter,adapter3;
    private AlbumsAdapter adapter2;

    private List<Album> albumList,albumList3;
    private List<Album> albumList2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_news, container, false);
        home_best_sellings = root.findViewById(R.id.home_best_sellings);
        home_trending = root.findViewById(R.id.home_trending);
        home_tv=root.findViewById(R.id.home_tv);
        albumList = new ArrayList<>();
        albumList2 = new ArrayList<>();
        albumList3= new ArrayList<>();
        adapter = new AlbumsAdapter(getContext().getApplicationContext(), albumList);
        adapter2 = new AlbumsAdapter(getContext().getApplicationContext(), albumList2);
        adapter3 = new AlbumsAdapter(getContext().getApplicationContext(), albumList3);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mmLayoutManager = new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mmmLayoutManager = new GridLayoutManager(getContext().getApplicationContext(), 2);

        home_best_sellings.setLayoutManager(mLayoutManager);
        home_best_sellings.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        home_best_sellings.setItemAnimator(new DefaultItemAnimator());
        home_trending.setLayoutManager(mmLayoutManager);
        home_trending.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        home_trending.setItemAnimator(new DefaultItemAnimator());
        home_tv.setLayoutManager(mmmLayoutManager);
        home_tv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        home_tv.setItemAnimator(new DefaultItemAnimator());

        prepareAlbums();

        try {
            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/try-on-store-27254.appspot.com/o/bannerone.png?alt=media&token=48cf21d0-adc3-4425-940b-a09b48e098ac").into((ImageView) root.findViewById(R.id.backdrop));
            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/try-on-store-27254.appspot.com/o/bannerone.png?alt=media&token=48cf21d0-adc3-4425-940b-a09b48e098ac").into((ImageView) root.findViewById(R.id.backdrop2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    private void prepareAlbums() {
        fstore.collection("best sellings").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                albumList.clear();

                for (DocumentSnapshot snapshot : value) {
                    Album a = new Album(snapshot.getString("Title"), snapshot.getString("Description"), snapshot.getString("Prize"), snapshot.getString("Image"));
                    albumList.add(a);
                    Album a3 = new Album(snapshot.getString("Title"), snapshot.getString("Description"), snapshot.getString("Prize"), snapshot.getString("Image"));
                    albumList3.add(a3);
                    Album a2 = new Album(snapshot.getString("Title"), snapshot.getString("Description"), snapshot.getString("Prize"), snapshot.getString("Image"));
                    albumList2.add(a2);
                    home_best_sellings.setAdapter(adapter);
                    home_trending.setAdapter(adapter2);
                    home_tv.setAdapter(adapter3);
                }
            }
        });
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onStart() {

//        home_trending.setAdapter(adapter2);
        prepareAlbums();
        super.onStart();


    }
}
