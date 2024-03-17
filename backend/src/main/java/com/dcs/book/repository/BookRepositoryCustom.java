package com.dcs.book.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface BookRepositoryCustom {
    EntityManager getEntityManager();

}
