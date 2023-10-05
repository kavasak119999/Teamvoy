package com.teamvoy.services;

import com.teamvoy.entity.Goods;
import com.teamvoy.exceptions.NotFoundException;
import com.teamvoy.repository.GoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    private final GoodsRepository goodsRepository;

    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Goods addGoods(Goods goods) {
        logger.info("Adding goods with ID: " + goods.getId());
        Goods savedGoods = goodsRepository.save(goods);
        logger.info("Goods with ID {} has just been added", goods.getId());
        return savedGoods;
    }

    @Override
    public List<Goods> listAvailableGoods() {
        logger.info("Listing available goods");
        return goodsRepository.findAll();
    }

    @Override
    public Goods getGoodsById(Long id) {
        logger.info("Getting goods by ID: " + id);
        return goodsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

}