package com.dcs.book.repository;

import com.dcs.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
//    List<RateExchange> findByCountryAndRecordDateBetweenOrderByRecordDateDesc(String country, Date startDate, Date recordDate);
}

