package com.dcs.book.repository;

import com.dcs.book.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Repository implementation class methods to manipulate {@link Book} resource in database.
 * This class inherits from {@link BookRepositoryCustom}
 *
 * @see BookRepositoryCustom
 */
@Repository
@Slf4j
public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}