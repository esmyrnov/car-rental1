package com.carrental.carrental.config;

import com.carrental.carrental.entity.Car;
import com.carrental.carrental.entity.Role;
import com.carrental.carrental.entity.User;
import com.carrental.carrental.repository.CarRepository;
import com.carrental.carrental.repository.RoleRepository;
import com.carrental.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final CarRepository carRepo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        Role userRole = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(role("ROLE_USER")));
        Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(role("ROLE_ADMIN")));

        userRepo.findByEmail("admin@local.com").orElseGet(() -> {
            User a = new User();
            a.setFullName("Admin");
            a.setEmail("admin@local.com");
            a.setPhone("+380000000000");
            a.setPassword(encoder.encode("password"));
            a.getRoles().add(adminRole);
            a.getRoles().add(userRole);
            return userRepo.save(a);
        });

        if (carRepo.count() == 0) {
            carRepo.save(car("Toyota","Corolla",2023,1200,"Comfort",true));
            carRepo.save(car("Hyundai","Elantra",2024,1450,"Popular",true));
            carRepo.save(car("Skoda","Octavia",2022,1050,"Economy",true));
            carRepo.save(car("BMW","X5",2021,2600,"SUV",false));
            carRepo.save(car("Volkswagen","Passat",2022,1350,"Comfort",true));
            carRepo.save(car("Kia","Sportage",2023,1550,"SUV",true));
        }
    }

    private Role role(String name) { Role r = new Role(); r.setName(name); return r; }

    private Car car(String make, String model, int year, double price, String category, boolean available) {
        Car c = new Car();
        c.setMake(make);
        c.setModel(model);
        c.setYear(year);
        c.setPricePerDay(price);
        c.setCategory(category);
        c.setAvailable(available);
        c.setDescription("Комфортний автомобіль для міських поїздок та подорожей.");
        return c;
    }
}
