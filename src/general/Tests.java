package general;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tests {

	Quotely q;
	
	@BeforeEach 
	public void setup() {
		q = new Quotely();
	}
	
	@Test
	public void testQuotelyMethodEnglish() throws ParseException, IOException {
	    System.setIn(new ByteArrayInputStream("English".getBytes()));
	    String result = q.quotelyMethod();
	    assertNotNull(result);
	    assertTrue(result.matches("^Quote(.*)\nAuthor(.*)$"));
	}
	
	@Test
	public void testQuotelyMethodRussian() throws ParseException, IOException {
	    System.setIn(new ByteArrayInputStream("Russian".getBytes()));
	    String result = q.quotelyMethod();
	    assertNotNull(result);
	    assertTrue(result.matches("^Quote(.*)\nAuthor(.*)$"));
	}

	@Test
	public void testQuotelyMethodDefault() throws ParseException, IOException {
	    System.setIn(new ByteArrayInputStream("\n".getBytes()));
	    String result = q.quotelyMethod();
	    assertNotNull(result);
	    assertTrue(result.matches("^Quote(.*)\nAuthor(.*)$"));
	}
	
	@Test
	public void testQuotelyThrowIllegalArgument() {
	    System.setIn(new ByteArrayInputStream("Chinese".getBytes()));
	    assertThrows(IllegalArgumentException.class, ()-> q.quotelyMethod());
	}
}
