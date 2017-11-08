package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Tiles.Letter;
import ch.heigvd.wordoff.server.Repository.LetterRepository;
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

@RestController
@RequestMapping(value = "/letters", produces = "application/json")
public class LetterController {

    private LetterRepository letterRepository;

    public LetterController(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    @RequestMapping(value = "/{lang}", method = RequestMethod.GET)
    public HttpEntity<List<Character>> getLetters(@PathVariable("lang") String lang) {
        List<Letter> letters = letterRepository.findAllByLangSetName(lang);
        if(letters == null || letters.isEmpty()) {
            throw new ErrorCodeException(Protocol.LANG_NOT_EXISTS, "The language " + lang + " does not exist!");
        }
        List<Character> lettersDto = new ArrayList<>();

        for(Letter l : letters) {
            lettersDto.add(l.getValue());
        }

        return new ResponseEntity<>(lettersDto, HttpStatus.OK);
    }
}
