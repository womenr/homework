package wj.reading.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class WordParserUtils {
	public static List<String> contentToWordList(String content) {
		
		List<String> resultList = new ArrayList<String>();
		
		Tokenizer tokenizer = new Tokenizer();
		Set<Token> tokenSet = new HashSet<Token>();
		Integer counter = 0;
		
		tokenSet.addAll(tokenizer.tokenize(content));
		
		for (Token token : tokenSet) {
			resultList.add("verb : " + token.getBaseForm() + " | pronunciation : " + token.getPronunciation());
			counter++;
		}
		
		return resultList;
	}
}
