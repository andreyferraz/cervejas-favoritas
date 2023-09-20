package com.andreyferraz.cervejasfavoritas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andreyferraz.cervejasfavoritas.model.Beer;
import com.andreyferraz.cervejasfavoritas.service.BeerService;

@RestController
@RequestMapping("/beers")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping
    public List<Beer> listBeers() {
        return beerService.getAllBeers();
    }

    @PostMapping("/{beerId}/add-consumer")
    public ResponseEntity<String> addConsumerToBeer(
            @PathVariable Long beerId,
            @RequestParam String consumerName) {
        beerService.addConsumerToBeer(beerId, consumerName);
        return ResponseEntity.ok("Consumer added to beer");
    }

    @PutMapping("/{beerId}/update-consumer/{consumerIndex}")
    public ResponseEntity<String> updateConsumerOfBeer(
            @PathVariable Long beerId,
            @PathVariable int consumerIndex,
            @RequestParam String newConsumerName) {
        beerService.updateConsumerOfBeer(beerId, consumerIndex, newConsumerName);
        return ResponseEntity.ok("Consumer updated");
    }

    @DeleteMapping("/{beerId}/remove-consumer/{consumerIndex}")
    public ResponseEntity<String> removeConsumerFromBeer(
            @PathVariable Long beerId,
            @PathVariable int consumerIndex) {
        beerService.removeConsumerFromBeer(beerId, consumerIndex);
        return ResponseEntity.ok("Consumer removed from beer");
    }

}
