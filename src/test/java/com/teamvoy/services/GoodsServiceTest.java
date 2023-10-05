package com.teamvoy.services;

import com.teamvoy.entity.Goods;
import com.teamvoy.repository.GoodsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GoodsServiceTest {

    @InjectMocks
    private GoodsServiceImpl goodsService;

    @Mock
    private GoodsRepository goodsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddGoods() {
        Goods newGoods = new Goods("Iphone 1", 500.0, 10);

        // Mock the behavior of goodsRepository.save
        when(goodsRepository.save(newGoods)).thenReturn(newGoods);

        // Act
        Goods savedGoods = goodsService.addGoods(newGoods);

        // Assert
        assertEquals(newGoods, savedGoods);
        verify(goodsRepository, times(1)).save(newGoods);
    }

    @Test
    public void testListAvailableGoods() {
        // Arrange
        List<Goods> availableGoods = new ArrayList<>();
        availableGoods.add(new Goods("Iphone 1", 200.0, 5));
        availableGoods.add(new Goods("Iphone 2", 150.0, 10));

        // Mock the behavior of goodsRepository.findAll
        when(goodsRepository.findAll()).thenReturn(availableGoods);

        // Act
        List<Goods> result = goodsService.listAvailableGoods();

        // Assert
        assertEquals(availableGoods.size(), result.size());
        verify(goodsRepository, times(1)).findAll();
    }

}