package com.intertecintl.repository;

import org.springframework.data.repository.CrudRepository;

import com.intertecintl.model.RestrictedWord;

public interface RestrictedWordRepository extends CrudRepository<RestrictedWord, Long> {

	RestrictedWord findByWord(String word);
}
