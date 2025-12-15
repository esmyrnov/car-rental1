package com.carrental.carrental.service;

import com.carrental.carrental.dto.SearchDto;
import com.carrental.carrental.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> search(SearchDto dto);
    Car get(Long id);
    List<Car> all();
    Car save(Car car);
    void delete(Long id);
    long count();
}
