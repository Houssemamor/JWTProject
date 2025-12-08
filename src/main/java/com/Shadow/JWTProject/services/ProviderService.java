package com.Shadow.JWTProject.services;

import com.Shadow.JWTProject.entities.Provider;
import com.Shadow.JWTProject.repositories.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProviderService {

    private final ProviderRepository repo;

    public ProviderService(ProviderRepository repo) {
        this.repo = repo;
    }

    public List<Provider> getAll() {
        return repo.findAll();
    }

    public Provider getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Provider not found: " + id));
    }

    public Provider create(Provider provider) {
        return repo.save(provider);
    }

    public Provider update(Long id, Provider provider) {
        Provider existing = getById(id);
        existing.setName(provider.getName());
        existing.setSalary(provider.getSalary());
        existing.setPhone(provider.getPhone());
        existing.setAge(provider.getAge());
        existing.setEmail(provider.getEmail());
        existing.setPassword(provider.getPassword());
        existing.setMatricule(provider.getMatricule());
        existing.setService(provider.getService());
        existing.setCompany(provider.getCompany());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
