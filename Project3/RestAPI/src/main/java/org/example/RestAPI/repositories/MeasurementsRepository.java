package org.example.RestAPI.repositories;

import org.example.RestAPI.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    public List<Measurement> findAllByRaining(boolean raining);
}
