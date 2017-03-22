package com.intertecintl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intertecintl.model.RestrictedWord;
import com.intertecintl.repository.RestrictedWordRepository;
import com.intertecintl.service.RestrictedWordService;

@Service
public class RestrictedWordServiceImpl implements RestrictedWordService {

	@Autowired
	RestrictedWordRepository restrictedWordRepo;

	@Override
	public void saveRestrictedWord(RestrictedWord word) {
		RestrictedWord restrictedWord = restrictedWordRepo.findByWord(word.getWord());
		if(restrictedWord==null)
		restrictedWordRepo.save(word);
	}

	@Override
	public List<RestrictedWord> findAllRestrictedWord() {
		return (List<RestrictedWord>) restrictedWordRepo.findAll();
	}

	@Override
	public RestrictedWord findById(long id) {
		return restrictedWordRepo.findOne(id);
	}

	@Override
	public void deleteRestrictedWordById(long id) {
		restrictedWordRepo.delete(id);
	}

	@Override
	public RestrictedWord findByWord(String word) {
		return restrictedWordRepo.findByWord(word);
	}

}
