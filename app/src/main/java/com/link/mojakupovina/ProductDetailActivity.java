package com.link.mojakupovina;


import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView name, description, price;
    private ImageView image;
    private Button addToCartButton;
    private AppDatabase db;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        name = findViewById(R.id.product_name);
        description = findViewById(R.id.product_description);
        price = findViewById(R.id.product_price);
        image = findViewById(R.id.product_image);
        addToCartButton = findViewById(R.id.add_to_cart_button);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();

        int productId = getIntent().getIntExtra("productId", -1);
        if (productId != -1) {
            product = db.productDao().findById(productId);
            name.setText(product.name);
            description.setText(product.description);
            price.setText(String.valueOf(product.price));
            Uri imageUri = Uri.parse(product.imageUri);
            image.setImageURI(imageUri);
        }

        addToCartButton.setOnClickListener(v -> {
            Cart cart = new Cart();
            cart.productId = product.id;
            cart.quantity = 1;
            db.cartDao().insertAll(cart);
            Toast.makeText(this, "Proizvod je dodat u korpu", Toast.LENGTH_SHORT).show();
        });



    }
}