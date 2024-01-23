package general;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Quotely {

	public final static String quoteUrl = "http://api.forismatic.com/api/1.0/?method=getQuote";
	
	public void quotelyMethod() {
		//TODO: Add continuous checking input with while (true)?
		try (Scanner scanner = new Scanner(System.in)) {
			String language = scanner.nextLine();
			 
			 if (language == null) {
				 language = "English";
			 }
			 
			 final String processLanguage  = language.trim();
			 
			 if (!(processLanguage.equals("English") || processLanguage.equals("Russian")) ) {
				 throw new IllegalArgumentException("Only English or Russian are acceptable input languages");
			 }
//			Fetches results from the forismatic.com quote api (http://forismatic.com/en/api/)
			final String response = this.fetchQuote(ResponseFormat.json, this.getLanguageParamValue(processLanguage));
//			//convert to JsonObject
//			final JSONParser jsonParser = new JSONParser();
//
//			final JsonObject jsonObject = jsonParser.parse(response);
//			
////			Displays to stdout the quote and author
//			 this.displayResult(jsonObject);
		}
	}
	private String getLanguageParamValue(String language) {
		if (language.equals("English")) {
			return "en";
		} else {
			return "ru";
		}
	}
	private String fetchQuote(ResponseFormat format, String languageParam) {
	
		final String fullQuoteUrl = quoteUrl + "&format=" + format;
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fullQuoteUrl.replace(" ", "%20")))
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
	
	
//	private void displayResult(JSONObject jsonObject) {
//		System.out.println("Quote: " + jsonObj.get("quoteText"));
//		System.out.println("Author: " + jsonObj.get("quoteAuthor"));
//		System.out.println("");
//	}
}
