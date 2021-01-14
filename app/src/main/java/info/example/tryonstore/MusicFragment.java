package info.example.tryonstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;


public class MusicFragment extends Fragment {
    Button logout;
    ImageView Profile;
    TextView name, userId;
    View root;
    FirebaseAuth  mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_music, container, false);
        logout = root.findViewById(R.id.logout);
        Profile = root.findViewById(R.id.profile);
        name=root.findViewById(R.id.name);
        userId= root.findViewById(R.id.userid);
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getActivity());
        name.setText(account.getDisplayName());
        userId.setText(account.getEmail());
        if(account.getPhotoUrl() != null) {
            Glide.with(getActivity()).load(account.getPhotoUrl()).into(Profile);
        }else
        {
           Glide.with(getActivity()).load("www.firebasestorage.googleapis.com/v0/b/try-on-store-27254.appspot.com/o/WhatsApp%20Image%202020-11-18%20at%201.37.41%20PM.jpeg?alt=media&token=d635cf0c-c8b9-41e2-a146-b941d704ca46").into(Profile);
        }

        mAuth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();

                mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() == null && mAuth.getCurrentUser() == null) {
                    // User is logged in
                    startActivity(new Intent(getActivity(), LoginPage.class));
                    getActivity().finish();

                }


            }
        });
        return root;

    }

}
