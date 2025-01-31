package org.example.SecondSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN')") //ограничение с помощью аннотации
    public void doAdminStuff() {
        System.out.println("Admin stuff");
    }
}
