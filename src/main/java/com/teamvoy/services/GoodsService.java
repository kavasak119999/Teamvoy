package com.teamvoy.services;

import com.teamvoy.entity.Goods;

import java.util.List;

public interface GoodsService {
    Goods addGoods(Goods goods);
    List<Goods> listAvailableGoods();
    Goods getGoodsById(Long id);
}