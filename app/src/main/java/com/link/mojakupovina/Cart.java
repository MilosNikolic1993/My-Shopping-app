package com.link.mojakupovina;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cart {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int productId;
    public int quantity;
}