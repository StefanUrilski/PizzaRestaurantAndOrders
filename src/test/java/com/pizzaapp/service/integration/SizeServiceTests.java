package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.items.pizza.Size;
import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;
import com.pizzaapp.repository.menu.SizeRepository;
import com.pizzaapp.service.SizeService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SizeServiceTests extends TestBase {

    @MockBean
    SizeRepository sizeRepository;

    @Autowired
    SizeService service;

    @Test
    public void addSize_shouldAddSize() {
        SizeServiceModel size = new SizeServiceModel();
        size.setSize("sizeName");

        when(sizeRepository.findBySize("sizeName"))
                .thenReturn(Optional.empty());

        service.addSize(size);
        verify(sizeRepository)
                .save(any());
    }

    @Test
    public void getSizeById_whenIdExist_shouldReturnSameSize(){
        Size size = new Size();
        size.setSize("SizeName");

        when(sizeRepository.findBySize("size"))
                .thenReturn(Optional.of(size));

        SizeServiceModel sizeService = service.getBySizeName("size");

        assertEquals(size.getSize(), sizeService.getSize());
    }

    @Test
    public void getAllSizes_shouldReturnAllSizes() {
        Size s1 = new Size();
        s1.setSize("Small");

        Size s2 = new Size();
        s2.setSize("Large");

        List<Size> actual = new ArrayList<>(List.of(s1, s2));

        when(sizeRepository.findAll())
                .thenReturn(actual);

        List<SizeServiceModel> expected = service.getAllSizes();

        assertEquals(actual.size(), expected.size());
    }
}
