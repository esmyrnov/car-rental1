package com.carrental.carrental.repository;

import com.carrental.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("""
        select c from Car c
        where (:q is null or lower(concat(c.make,' ',c.model)) like lower(concat('%',:q,'%')))
          and (:category is null or c.category = :category)
          and (:available is null or c.available = :available)
          and (:yearFrom is null or c.year >= :yearFrom)
          and (:yearTo is null or c.year <= :yearTo)
          and (:budget is null or c.pricePerDay <= :budget)
        order by c.available desc, c.pricePerDay asc
    """)
    List<Car> search(@Param("q") String q,
                     @Param("category") String category,
                     @Param("available") Boolean available,
                     @Param("yearFrom") Integer yearFrom,
                     @Param("yearTo") Integer yearTo,
                     @Param("budget") Double budget);
}
