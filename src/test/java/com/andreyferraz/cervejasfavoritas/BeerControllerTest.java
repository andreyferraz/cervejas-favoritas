package com.andreyferraz.cervejasfavoritas;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import javax.swing.Spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.andreyferraz.cervejasfavoritas.model.Beer;
import com.andreyferraz.cervejasfavoritas.service.BeerService;

@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc
public class BeerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @Test
    public void testListBeers() throws Exception {

        Beer beer1 = new Beer();
        beer1.setId(1L);
        beer1.setName("Beer 1");
        Beer beer2 = new Beer();
        beer2.setId(2L);
        beer2.setName("Beer 2");

        List<Beer> beers = Arrays.asList(beer1, beer2);

        when(beerService.getAllBeers()).thenReturn(beers);

        mockMvc.perform(MockMvcRequestBuilders.get("/beers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("[{\"id\":1,\"name\":\"Beer 1\"},{\"id\":2,\"name\":\"Beer 2\"}]"));
    }

    @Test
    public void testAddConsumerToBeer() throws Exception {
        Long beerId = 1L;
        String consumerName = "John Doe";

        doNothing().when(beerService).addConsumerToBeer(beerId, consumerName);

        mockMvc.perform(MockMvcRequestBuilders.post("/beers/{beerId}/add-consumer", beerId)
                .param("consumerName", consumerName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Consumer added to beer"));

        verify(beerService, times(1)).addConsumerToBeer(beerId, consumerName);
    }

    @Test
    public void testUpdateConsumerOfBeer() throws Exception {
        
        Long beerId = 1L;
        int consumerIndex = 0;
        String newConsumerName = "Jane Doe";

        doNothing().when(beerService).updateConsumerOfBeer(beerId, consumerIndex, newConsumerName);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/beers/{beerId}/update-consumer/{consumerIndex}", beerId, consumerIndex)
                        .param("newConsumerName", newConsumerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Consumer updated"));

        verify(beerService, times(1)).updateConsumerOfBeer(beerId, consumerIndex, newConsumerName);

    }

    @Test
    public void testRemoveConsumerFromBeer() throws Exception {
    
        Long beerId = 1L;
        int consumerIndex = 0;

        doNothing().when(beerService).removeConsumerFromBeer(beerId, consumerIndex);

        mockMvc.perform(MockMvcRequestBuilders.delete("/beers/{beerId}/remove-consumer/{consumerIndex}", beerId, consumerIndex)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Consumer removed from beer"));

        verify(beerService, times(1)).removeConsumerFromBeer(beerId, consumerIndex);
    }

}
