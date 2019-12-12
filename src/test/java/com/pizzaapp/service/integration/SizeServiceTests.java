package com.pizzaapp.service.integration;

import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;
import com.pizzaapp.repository.menu.SizeRepository;
import com.pizzaapp.service.SizeService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SizeServiceTests extends TestBase {

    @MockBean
    SizeRepository sizeRepository;

    @Autowired
    SizeService sizeService;

    @Test
    public void addSize_shouldAddSize() {
        SizeServiceModel size = new SizeServiceModel();
        size.setSize("sizeName");

        when(sizeRepository.findBySize("sizeName"))
                .thenReturn(Optional.empty());

        sizeService.addSize(size);
        verify(sizeRepository)
                .save(any());
    }
}
