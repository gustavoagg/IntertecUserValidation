package com.intertecintl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.intertecintl.model.RestrictedWord;
import com.intertecintl.service.RestrictedWordService;


@RestController
public class RestrictedWordController {
	@Autowired
	private RestrictedWordService restrictedWordService;

	/**
	 * Request all restricted words
	 * 
	 * @return ResponseEntity<List<RestrictedWord>> with all the restricted words in the repository
	 */
	@RequestMapping(value = "/restricted/", method = RequestMethod.GET)
	public ResponseEntity<List<RestrictedWord>> listAllRestrictedWords() {
		List<RestrictedWord> words = restrictedWordService.findAllRestrictedWord();
		if (words.isEmpty()) {
			return new ResponseEntity<List<RestrictedWord>>(HttpStatus.NO_CONTENT);// or
			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<RestrictedWord>>(words, HttpStatus.OK);
	}

	/**
	 * Request a RestrictedWord by its Id
	 * 
	 * @param id
	 * @return ResponseEntity<RestrictedWord> associated to the given id
	 */
	@RequestMapping(value = "/restricted/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestrictedWord> getRestrictedWord(@PathVariable("id") long id) {
		RestrictedWord word = restrictedWordService.findById(id);
		if (word == null) {
			return new ResponseEntity<RestrictedWord>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RestrictedWord>(word, HttpStatus.OK);
	}
	
	/**
	 * Request a RestrictedWord by its Id
	 * 
	 * @param id
	 * @return ResponseEntity<RestrictedWord> associated to the given id
	 */
	@RequestMapping(value = "/restricted/add/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestrictedWord> addRestrictedWord(@PathVariable("word") String word,
			UriComponentsBuilder ucBuilder) {
		RestrictedWord restrictedWord = new RestrictedWord();
		restrictedWord.setWord(word);
		restrictedWordService.saveRestrictedWord(restrictedWord);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/restricted/{id}").buildAndExpand(restrictedWord.getId()).toUri());

		return new ResponseEntity<RestrictedWord>(restrictedWord, HttpStatus.CREATED);
	}

	/**
	 * Create a new restricted word into the repository with the given word
	 * 
	 * @param word
	 * @param ucBuilder
	 * @return ResponseEntity with the status of the transaction
	 */
	@RequestMapping(value = "/restricted/", method = RequestMethod.POST)
	public ResponseEntity<RestrictedWord> createRestrictedWord(@RequestBody RestrictedWord word,
			UriComponentsBuilder ucBuilder) {
		restrictedWordService.saveRestrictedWord(word);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/restricted/{id}").buildAndExpand(word.getId()).toUri());

		return new ResponseEntity<RestrictedWord>(word, HttpStatus.CREATED);
	}

	/**
	 * Delete the Restricted Word specified by the id
	 * 
	 * @param id
	 * @return ResponseEntity with the status of the transaction
	 */
	@RequestMapping(value = "/restricted/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<RestrictedWord> deleteRestrictedWord(@PathVariable("id") long id) {

		RestrictedWord word = restrictedWordService.findById(id);
		if (word == null) {
			return new ResponseEntity<RestrictedWord>(HttpStatus.NOT_FOUND);
		}

		restrictedWordService.deleteRestrictedWordById(id);
		return new ResponseEntity<RestrictedWord>(HttpStatus.NO_CONTENT);
	}

}
