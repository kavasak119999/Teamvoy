package com.teamvoy.controller;

import com.teamvoy.entity.Goods;
import com.teamvoy.services.GoodsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("/add-new")
    public ResponseEntity<Goods> addGoods(@RequestBody Goods goods) {
        Goods savedGoods = goodsService.addGoods(goods);
        return new ResponseEntity<>(savedGoods, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Goods>> listAvailableGoods() {
        List<Goods> availableGoods = goodsService.listAvailableGoods();
        return new ResponseEntity<>(availableGoods, HttpStatus.OK);
    }

}