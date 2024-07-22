package com.link.mojakupovina;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id = :id LIMIT 1")
    Product findById(int id);

    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);
}