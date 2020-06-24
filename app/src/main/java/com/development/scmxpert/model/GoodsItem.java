package com.development.scmxpert.model;

import androidx.annotation.NonNull;

public class GoodsItem {
    private String goods_id;
    private String goods;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }
    @NonNull
    @Override
    public String toString() {
        return goods;
    }
}
