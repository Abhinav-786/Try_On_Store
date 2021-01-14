package info.example.tryonstore.tabfragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import info.example.tryonstore.R;
import info.example.tryonstore.recyclerone.Album;
import info.example.tryonstore.recyclerone.AlbumsAdapter;

public class PaintingsFragment extends Fragment {
    RecyclerView product_paintings;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_paintings, container, false);
        product_paintings=root.findViewById(R.id.product_paintings);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getContext().getApplicationContext(), albumList);

        RecyclerView.LayoutManager mmmLayoutManager = new GridLayoutManager(getContext().getApplicationContext(), 2);
        product_paintings.setLayoutManager(mmmLayoutManager);
        product_paintings.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        product_paintings.setItemAnimator(new DefaultItemAnimator());
        prepareAlbums();

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


                    product_paintings.setAdapter(adapter);

                }
            }
        });
        adapter.notifyDataSetChanged();


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