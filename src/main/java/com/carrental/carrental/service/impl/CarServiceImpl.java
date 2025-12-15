package com.carrental.carrental.service.impl;

import com.carrental.carrental.dto.SearchDto;
import com.carrental.carrental.entity.Car;
import com.carrental.carrental.repository.CarRepository;
import com.carrental.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepo;

    @Override
    public List<Car> search(SearchDto dto) {
        return carRepo.search(
                emptyToNull(dto.getQ()),
                emptyToNull(dto.getCategory()),
                dto.getAvailable(),
                dto.getYearFrom(),
                dto.getYearTo(),
                dto.getBudget()
        );
    }

    @Override public Car get(Long id) { return carRepo.findById(id).orElseThrow(); }
    @Override public List<Car> all() { return carRepo.findAll(); }
    @Override public Car save(Car car) { return carRepo.save(car); }
    @Override public void delete(Long id) { carRepo.deleteById(id); }
    @Override public long count() { return carRepo.count(); }

    private String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
