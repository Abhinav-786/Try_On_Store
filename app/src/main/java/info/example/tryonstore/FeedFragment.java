package info.example.tryonstore;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import info.example.tryonstore.tabfragment.ElectronicsFragment;
import info.example.tryonstore.tabfragment.FurnitiureFragment;
import info.example.tryonstore.tabfragment.PaintingsFragment;
import info.example.tryonstore.tabfragment.ShowcaseFragment;


public class FeedFragment extends Fragment {

    ViewPager pager2;
    ImageView product_banner;
    TabLayout mTabLayout;
    PagerAdapter adapter1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        pager2 = root.findViewById(R.id.tabviewpager);
        mTabLayout = root.findViewById(R.id.tablayout);
        product_banner=root.findViewById(R.id.product_banner);


        adapter1 = new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter1.addFragment(new FurnitiureFragment(), "Furniture");
        adapter1.addFragment(new ShowcaseFragment(), "Showcase");
        adapter1.addFragment(new PaintingsFragment(), "Paintings");
        adapter1.addFragment(new ElectronicsFragment(), "Electronics");
        pager2.setAdapter(adapter1);
        mTabLayout.setupWithViewPager(pager2);
        try {
            Glide.with(getContext().getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/try-on-store-27254.appspot.com/o/bannerone.png?alt=media&token=48cf21d0-adc3-4425-940b-a09b48e098ac").into((ImageView) root.findViewById(R.id.product_banner));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }



}
