package com.sotouch.myway.service.translation;
import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.sotouch.myway.Constants;

public class TranslationService {
	
	public String translate(String text, String sourceLanguage, String targetLanguage) throws DefaultTranslationException{
		if (sourceLanguage.toLowerCase().startsWith("english")) sourceLanguage = "english";
		else {
			sourceLanguage = sourceLanguage.split("-")[0];
		}
		if (targetLanguage.toLowerCase().startsWith("english")) targetLanguage = "english";
		else {
			targetLanguage = targetLanguage.split("-")[0];
		}
		
		System.out.println("Source language: '" + sourceLanguage + "' target language: " + targetLanguage);
		
		// Set the HTTP referrer to your website address.
	    GoogleAPI.setHttpReferrer(Constants.GOOGLE_API_REFERER);

	    // Set the Google Translate API key
	    // See: http://code.google.com/apis/language/translate/v2/getting_started.html
	    GoogleAPI.setKey(Constants.GOOGLE_API_KEY);

	    Language source = getLanguage(sourceLanguage);
	    Language target = getLanguage(targetLanguage);
	    String translatedText = "";
	    //System.out.println(text);
	    
	    if (source == null) {
			throw new DefaultTranslationException(sourceLanguage+" language unknown");
	    }
	    else if (target == null) {
			throw new DefaultTranslationException(targetLanguage+" language unknown");
	    }
	    else {
			try {
				if(!source.equals(target)){
					text = text.replace("\n", "<br/>");
					if(text.length() < Constants.GOOGLE_API_CHAR_LIMIT){
						translatedText = Translate.DEFAULT.execute(text, source, target) ;
					}
					else {
						
						int i= 0;
						while(i < text.length()) { // tant que tout le text n'a pas �t� parcouru
							
							String simpleText;
							
							if((i+Constants.GOOGLE_API_CHAR_LIMIT) >= text.length()) {
								simpleText = text.substring(i, text.length());
							}
							else{
								simpleText = text.substring(i, i+Constants.GOOGLE_API_CHAR_LIMIT); // on prend une certaine longueur de chaine dans ce texte
							}
							
							if(simpleText.contains(".")) { // Si cette sous chaine contient un point...
								int j = simpleText.lastIndexOf(".");
								simpleText = simpleText.substring(0, j+1); //...On consid�re le dernier point pour en retirer la/les phrase(s) correspondante(s) et l'utiliser comme sous chaine
								i += (j+1);
							}
							else {
								i += Constants.GOOGLE_API_CHAR_LIMIT; //Sinon on se contente de cette sous chaine
							}
							
							translatedText += Translate.DEFAULT.execute(simpleText, source, target); //On ajoute la traduction de cette sous chaine � la traduction finale
							
						}
		
					}
					
					translatedText = translatedText.replace("<br/>","\n");
					
				}
				else {
					translatedText = text;
				}
			}
			catch (GoogleAPIException e) {
				throw new DefaultTranslationException(e.getMessage());
			}
	    }

	    //System.out.println("'" + text + "' (" + sourceLanguage + ") translated to '"+ translatedText +"'(" + targetLanguage + ")");
	    return translatedText;
	}

	private Language getLanguage(String languageStr){
		Language language = null;
		try {
			language = Language.valueOf(languageStr.toUpperCase());
		}
		catch (Exception e) {
		}
		return language;
	}

}
