package com.odix.fr.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.odix.fr.security.JwtResponseModel;
import com.odix.fr.security.JwtTokenUtil;
import com.odix.fr.security.UserDetailsServiceImpl;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@Service
public class Linkedin {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private Environment env;
	
	private LinkedInConnectionFactory lfactory;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	String idLinkedin;
	String firstName;
	String lastName;
	String emailAddress;
	String profilePicture;
	
	public JSONObject codeLinkedin() {
		
        lfactory = new LinkedInConnectionFactory(env.getProperty("linkedin.consumerKey"), env.getProperty("linkedin.consumerSecret"));
		OAuth2Operations operations = lfactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
	    params.setRedirectUri(env.getProperty("linkedin.redirectUri"));
		params.setScope(env.getProperty("linkedin.scope"));
		params.setState(env.getProperty("linkedin.state"));

		String url = operations.buildAuthenticateUrl(params);
		
		JSONObject urlJson = new JSONObject();
		urlJson.put("0", url);
		return urlJson;
	}

	public JSONObject redirectLinkedin(String code, String state) throws Exception {
		
        lfactory = new LinkedInConnectionFactory(env.getProperty("linkedin.consumerKey"), env.getProperty("linkedin.consumerSecret"));
		OAuth2Operations operations = lfactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope(env.getProperty("linkedin.scope"));
		params.setState(env.getProperty("linkedin.state"));
		
		// Faire une boucle Try afin de traiter les bugs API LinkedIn
		try {
			AccessGrant accessToken = operations.exchangeForAccess(code, env.getProperty("linkedin.redirectUri"), params);
			System.out.println("AccessToken is : " + accessToken.getAccessToken());
			
			//### r_liteprofile
			JSONObject jsonObject = (JSONObject)callToLinkedIn(env.getProperty("linkedin.urlLiteProfile"), accessToken); 
			idLinkedin = (String)jsonObject.get("id");
			firstName = (String)jsonObject.get("localizedFirstName");
			lastName = (String)jsonObject.get("localizedLastName");
			
			//### r_emailaddress
			jsonObject = (JSONObject)callToLinkedIn(env.getProperty("linkedin.urlEmailaddress"), accessToken); 
			
			for (Object object : (JSONArray)jsonObject.get("elements")) {
				JSONObject objectCasted = (JSONObject) object;
				JSONObject email = (JSONObject) objectCasted.get("handle~");
				emailAddress = (String)email.get("emailAddress");
			}
			
			//### ProfilePicture
			jsonObject = (JSONObject)callToLinkedIn(env.getProperty("linkedin.urlProfilePicture"), accessToken); 
			
			JSONObject mapProfilePicture = (JSONObject)jsonObject.get("profilePicture");
			JSONObject displayImage = (JSONObject) mapProfilePicture.get("displayImage~");

			for (Object object : (JSONArray)displayImage.get("elements")) {
				JSONObject objectCasted = (JSONObject) object;
				JSONArray identifiers = (JSONArray) objectCasted.get("identifiers");
				JSONObject identifiers0 = (JSONObject) identifiers.get(0);
				profilePicture = (String)identifiers0.get("identifier");
			}
		}catch(Exception e) {
			return null;
		}
		
		//Remlissage du JSONObject Final
		JSONObject jsonProfile = new JSONObject();
		jsonProfile.put("idLinkedin", idLinkedin);
		jsonProfile.put("firstName", firstName);
		jsonProfile.put("lastName", lastName);
		jsonProfile.put("emailAddress", emailAddress);
		jsonProfile.put("profilePicture", profilePicture);
		
		return jsonProfile;
	}
	
	public JSONObject callToLinkedIn(String url, AccessGrant accessToken) throws Exception {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("Host", "api.linkedin.com");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Authorization", "Bearer "+ accessToken.getAccessToken());
		
		BufferedReader bR = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = "";
		StringBuilder responseStrBuilder = new StringBuilder();
		
		while((line =  bR.readLine()) != null){

		    responseStrBuilder.append(line);
		}
		con.getInputStream().close();
		
		@SuppressWarnings("deprecation")
		Object json = new JSONParser().parse(responseStrBuilder.toString()); 
		
		return (JSONObject) json;
	}
	
	public ResponseEntity<?> createAuthenticationToken(String username, String password) throws Exception {

		/*
		 * ICI on ne fait pas appel à la méthode authenticate(username, password) comme on fait si le login provient d'un formulaire (voir méthode 
		 * createAuthenticationToken de JwtAuthenticationController.
		 * Raison : le login provient dèja de Linkedin, donc la personne est dèja authentifiée, en plus si on veut récupérer le mot de passe
		 * de cette personne à partir de la base, on va récupérer le mot de passe codé, et vu qu'il est codé alors il ne sera pas reconnu par spring security
		 * on aura une erreur 401 (utilisateur non reconnu) car Spring va coder le password déja codé et va le comparer, alors le résultat sera négatif
		 */
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		logger.warn("JWT Token has been created");

		return ResponseEntity.ok(new JwtResponseModel(token));
	}
}

