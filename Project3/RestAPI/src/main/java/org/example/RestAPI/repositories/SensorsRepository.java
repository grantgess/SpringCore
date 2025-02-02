package org.example.RestAPI.repositories;

import org.example.RestAPI.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    public Optional<Sensor> findByName(String name);
}
