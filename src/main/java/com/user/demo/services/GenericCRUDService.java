package com.user.demo.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

public class GenericCRUDService<T, ID> {

  private final JpaRepository<T, ID> repository;

  protected GenericCRUDService(JpaRepository<T, ID> repository) {
    this.repository = repository;
  }

  public List<T> findAll() {
    return repository.findAll();
  }

  public T findById(ID id) throws EntityNotFoundException {
    return repository.findById(id)
                     .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
  }

  public T save(T entity) {
    return repository.save(entity);
  }

  public void deleteById(ID id) {
    repository.deleteById(id);
  }

}
