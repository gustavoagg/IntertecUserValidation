package com.intertecintl.service;

import java.util.List;

import com.intertecintl.model.RestrictedWord;

public interface RestrictedWordService {

	RestrictedWord findById(long id);

	RestrictedWord findByWord(String word);

	void deleteRestrictedWordById(long id);

	void saveRestrictedWord(RestrictedWord word);

	List<RestrictedWord> findAllRestrictedWord();
}
