package general;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tests {

	Quotely q;
	
	@BeforeEach 
	public void setup() {
		q = new Quotely();
	}
	@Test
	public void testBarCorrect() {
	    System.setIn(new ByteArrayInputStream("English".getBytes()));
	    Scanner scanner = new Scanner(System.in);
	    q.quotelyMethod();
	}

}
