package com.link.mojakupovina;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private AppDatabase db;
    private LinearLayout cartList;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();

        cartList = findViewById(R.id.cart_list);
        checkoutButton = findViewById(R.id.checkout_button);

        loadCart();

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.cartDao().deleteAll();
                Toast.makeText(CartActivity.this, "Kupovina završena", Toast.LENGTH_SHORT).show();
                loadCart();
            }
        });
    }

    private void loadCart() {
        cartList.removeAllViews();
        List<Cart> carts = db.cartDao().getAll();
        for (Cart cart : carts) {
            View view = getLayoutInflater().inflate(R.layout.cart_item, null);
            // Popunjavanje podataka u pogledu, kao što je naziv proizvoda, cena itd.
            cartList.addView(view);
        }
    }
}