package com.link.mojakupovina;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart")
    List<Cart> getAll();

    @Insert
    void insertAll(Cart... carts);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM cart")
    void deleteAll();
}