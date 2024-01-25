package general;

import java.io.IOException;
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
	
	public String quotelyMethod() throws ParseException, IOException {
		try (Scanner input = new Scanner(System.in)) {
			String command = input.nextLine();
			 
			 if (command == null || command.trim().equals("")) {
				 command = "English";
			 }
			 final String processLanguage  = command.trim();
			 
			 if (!(processLanguage.equals("English") || processLanguage.equals("Russian")) ) {
				 throw new IllegalArgumentException("Only English or Russian are acceptable input languages");
			 }
			final String response = this.fetchQuote(ResponseFormat.json, this.getLanguageParamValue(processLanguage));
			final JSONParser jsonParser = new JSONParser();

		
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
			return this.displayResult(jsonObject);
		}
	}
	
	private String getLanguageParamValue(String language) {
		if (language.equals("English")) {
			return "en";
		} else if (language.equals("Russian")){
			return "ru";
		}
		throw new IllegalArgumentException("Only English or Russian are acceptable input languages");
	}
	
	private String fetchQuote(ResponseFormat format, String languageParam) throws IOException {
	
		final String fullQuoteUrl = quoteUrl + "&format=" + format +"&lang=" + languageParam;
		
		final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fullQuoteUrl.replace(" ", "%20")))
				.header("Content-Type", "application/json").method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt() ;
		} catch (IOException e) {
			e.printStackTrace();
			//Add retries in production code if SocketTimeoutException
			throw e;
		}
		return response.body().toString();
	}
	
	
	private String displayResult(JSONObject jsonObject) {
		String result = "Quote: " + jsonObject.get("quoteText") +"\nAuthor: " + jsonObject.get("quoteAuthor");
		System.out.println(result);
		return result;
	}
}
