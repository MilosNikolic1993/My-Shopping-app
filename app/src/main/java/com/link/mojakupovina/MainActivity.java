package com.link.mojakupovina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout productList;
    private Button addProductButton, viewCartButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = findViewById(R.id.product_list);
        addProductButton = findViewById(R.id.add_product_button);
        viewCartButton = findViewById(R.id.view_cart_button);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();

        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        loadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        productList.removeAllViews();
        List<Product> products = db.productDao().getAll();
        for (Product product : products) {
            View productView = getLayoutInflater().inflate(R.layout.product_item, null);
            productView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId", product.id);
                startActivity(intent);
            });
            // Popunite UI elemente sa podacima o proizvodu
            // npr. TextView nameTextView = productView.findViewById(R.id.product_name);
            // nameTextView.setText(product.name);
            productList.addView(productView);
        }
    }
}