package info.example.tryonstore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Product_Details_activity extends Activity implements PaymentResultListener {
ImageView image_product;
TextView title, description, prize,title2, description2, prize2;
Button pay;
String image,Title,Description,Prize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details_activity);
        image_product=findViewById(R.id.product_image);
        title=findViewById(R.id.title_product);
        description=findViewById(R.id.description_product);
        prize= findViewById(R.id.prize_product);
        title2=findViewById(R.id.title_product2);
        description2=findViewById(R.id.description_product2);
        prize2= findViewById(R.id.prize_product2);
        pay=findViewById(R.id.pay);
        Title= getIntent().getStringExtra("title");
        Description=getIntent().getStringExtra("description");
        image=getIntent().getStringExtra("image");
        Prize=getIntent().getStringExtra("prize");

         title.setText(Title);
         description.setText(Description);
         prize.setText(Prize);
        title2.setText(Title);
        description2.setText(Description);
        prize2.setText(Prize);

        Glide.with(this).load(image).into((ImageView) findViewById(R.id.product_image));

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout=new Checkout();
                checkout.setKeyID("rzp_test_Yfe6pLBsgQqv0a");
                checkout.setImage(R.mipmap.ic_launcher);
                JSONObject object= new JSONObject();
                String Prize2=Prize+"";
//                int amount =Integer.parseInt(Prize2)*100;

                try{
                    object.put("name","Try On Store");
                    object.put("description", "Pay the payment");
                    object.put("theme.color","#fcfcfc");
                    object.put("currency", "INR");
                    object.put("amount",400*100);
                    object.put("prefill.contact","9041171625");
                    object.put("prefill.email","karanmatharoo24@gmail.com");
                    checkout.open(Product_Details_activity.this,object);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}