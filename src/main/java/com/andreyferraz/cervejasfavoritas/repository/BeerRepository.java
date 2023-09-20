package com.andreyferraz.cervejasfavoritas.repository;

import com.andreyferraz.cervejasfavoritas.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {
}
