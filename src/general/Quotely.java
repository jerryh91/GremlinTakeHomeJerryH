package general;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Quotely {

	public final static String quoteUrl = "http://api.forismatic.com/api/1.0/?method=getQuote";
	
	public void quotelyMethod() {
		//TODO: Add continuous checking input with while (true)?
//		while (true) {
		try (Scanner scanner = new Scanner(System.in)) {
			String language = scanner.nextLine();
			 
			 if (language == null || language.trim().equals("")) {
				 language = "English";
			 }
			 
			 final String processLanguage  = language.trim();
			 
			 if (!(processLanguage.equals("English") || processLanguage.equals("Russian")) ) {
				 throw new IllegalArgumentException("Only English or Russian are acceptable input languages");
			 }
			final String response = this.fetchQuote(ResponseFormat.json, this.getLanguageParamValue(processLanguage));
			final JSONParser jsonParser = new JSONParser();

			try {
				JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
				this.displayResult(jsonObject);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
//		}
		
	}
	
	private String getLanguageParamValue(String language) {
		if (language.equals("English")) {
			return "en";
		} else if (language.equals("Russian")){
			return "ru";
		}
		throw new IllegalArgumentException("Only English or Russian are acceptable input languages");
	}
	
	private String fetchQuote(ResponseFormat format, String languageParam) {
	
		final String fullQuoteUrl = quoteUrl + "&format=" + format +"&lang=" + languageParam;
		
		final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fullQuoteUrl.replace(" ", "%20")))
				.header("Content-Type", "application/json").method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return response.body().toString();
	}
	
	
	private void displayResult(JSONObject jsonObject) {
		System.out.println("Quote: " + jsonObject.get("quoteText"));
		System.out.println("Author: " + jsonObject.get("quoteAuthor"));
		System.out.println("");
	}
}
