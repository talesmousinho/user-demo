package com.user.demo.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public class GenericCRUDService<T, ID> {

  private final PagingAndSortingRepository<T, ID> repository;

  protected GenericCRUDService(PagingAndSortingRepository<T, ID> repository) {
    this.repository = repository;
  }

  public Page<T> findAll(int page, int size, Sort sort) {
    return repository.findAll(PageRequest.of(page, size, sort));
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
