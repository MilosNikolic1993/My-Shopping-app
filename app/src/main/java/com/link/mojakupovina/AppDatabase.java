package com.link.mojakupovina;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Cart.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
}