package com.andreyferraz.cervejasfavoritas.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andreyferraz.cervejasfavoritas.model.Beer;
import com.andreyferraz.cervejasfavoritas.repository.BeerRepository;

@Service
public class BeerService {

    private final WebClient webClient;
    private final BeerRepository beerRepository;

    public BeerService(WebClient.Builder webClientBuilder, BeerRepository beerRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.punkapi.com/v2/beers").build();
        this.beerRepository = beerRepository;
    }

    public List<Beer> getAllBeers() {

        Beer[] beers = webClient
            .get()
            .retrieve()
            .bodyToMono(Beer[].class)
            .block(); // Converte o Mono para uma lista síncrona

        // Salvar as cervejas no banco de dados
        if (beers != null) {
            for (Beer beer : beers) {
                beerRepository.save(beer);
            }
        }

        return Arrays.asList(beers);
    }

    public void addConsumerToBeer(Long beerId, String consumerName) {
        Optional<Beer> optionalBeer = beerRepository.findById(beerId);
        if (optionalBeer.isPresent()) {
            Beer beer = optionalBeer.get();
            beer.getConsumers().add(consumerName);
            beerRepository.save(beer);
        }
    }

    public void updateConsumerOfBeer(Long beerId, int consumerIndex, String newConsumerName) {
        Optional<Beer> optionalBeer = beerRepository.findById(beerId);
        if (optionalBeer.isPresent()) {
            Beer beer = optionalBeer.get();
            List<String> consumers = beer.getConsumers();
            if (consumerIndex >= 0 && consumerIndex < consumers.size()) {
                consumers.set(consumerIndex, newConsumerName);
                beerRepository.save(beer);
            }
        }
    }

    public void removeConsumerFromBeer(Long beerId, int consumerIndex) {
        Optional<Beer> optionalBeer = beerRepository.findById(beerId);
        if (optionalBeer.isPresent()) {
            Beer beer = optionalBeer.get();
            List<String> consumers = beer.getConsumers();
            if (consumerIndex >= 0 && consumerIndex < consumers.size()) {
                consumers.remove(consumerIndex);
                beerRepository.save(beer);
            }
        }
    }

}






