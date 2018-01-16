/*
 * File: LetterController.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Tiles.Letter;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller to recuperate the letters associated with a language.
 */
@RestController
@RequestMapping(value = "/letters", produces = "application/json")
public class LetterController {

    private LangSetRepository langSetRepository;

    public LetterController(LangSetRepository langSetRepository) {
        this.langSetRepository = langSetRepository;
    }

    /**
     * GET the letters associated with a given language.
     * @param lang The language.
     * @return The letters associated.
     */
    @RequestMapping(value = "/{lang}", method = RequestMethod.GET)
    public HttpEntity<List<Character>> getLetters(@PathVariable("lang") String lang) {
        LangSet langSet = langSetRepository.findByName(lang);
        if(langSet == null) {
            throw new ErrorCodeException(Protocol.LANG_NOT_EXISTS, "The language " + lang + " does not exist!");
        }
        List<Character> lettersDto = new ArrayList<>();

        for(Letter l : langSet.getLetters()) {
            lettersDto.add(l.getValue());
        }

        return new ResponseEntity<>(lettersDto, HttpStatus.OK);
    }
}
