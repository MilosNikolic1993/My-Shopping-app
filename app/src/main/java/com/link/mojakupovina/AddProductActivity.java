package com.link.mojakupovina;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import java.io.IOException;

public class AddProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nameEditText, descriptionEditText, priceEditText;
    private ImageView imageView;
    private Button selectImageButton, addButton;
    private Uri imageUri;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nameEditText = findViewById(R.id.name_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        priceEditText = findViewById(R.id.price_edit_text);
        imageView = findViewById(R.id.image_view);
        selectImageButton = findViewById(R.id.select_image_button);
        addButton = findViewById(R.id.add_button);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();

        selectImageButton.setOnClickListener(v -> openImageChooser());

        addButton.setOnClickListener(v -> addProduct());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addProduct() {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String priceStr = priceEditText.getText().toString();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Molimo popunite sve podatke", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.price = price;
        product.imageUri = imageUri.toString();

        db.productDao().insertAll(product);
        Toast.makeText(this, "Proizvod je dodat", Toast.LENGTH_SHORT).show();

        finish();
    }
}