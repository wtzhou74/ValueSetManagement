package valueset.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class NcboRestTest {

    static final String REST_URL = "http://data.bioontology.org";
    static final String API_KEY = "5e72f33e-aa16-40d1-b18b-59dd0f7a61c1";
    static final ObjectMapper mapper = new ObjectMapper();
    static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    public static void main(String[] args) throws Exception {
        ArrayList<String> terms = new ArrayList<String>();

        String currentDir = System.getProperty("user.dir");
        /*Scanner in = new Scanner(new FileReader(currentDir + "/src/classes_search_terms.txt"));

        while (in.hasNextLine()) {
            terms.add(in.nextLine());
        }*/
        terms.add("melanoma");
        //terms.add("lung");
        //terms.add("experiment");

        ArrayList<JsonNode> searchResults = new ArrayList<JsonNode>();
        for (String term : terms) {
            //JsonNode searchResult = jsonToNode(get(REST_URL + "/search?q=" + term + "&ontology=NCIT")).get("collection");
        	JsonNode searchResult = jsonToNode(get(REST_URL + "/search?cui=C0198927&ontologies=CPT")).get("collection");
            searchResults.add(searchResult);
        }

        for (JsonNode result : searchResults) {
        	System.out.println(result.path("collection"));result.findPath("links");
        	//System.out.println(writer.writeValueAsString(result.findParents("prefLabel")));C0198927
            //System.out.println(writer.writeValueAsString(result));
        	System.out.println(result.findPath("links").findValue("parents"));
        	jsonToNode(get("http://data.bioontology.org/ontologies/CPT/classes/http%3A%2F%2Fpurl.bioontology.org%2Fontology%2FCPT%2F00532/parents"));
        }
    }

    private static JsonNode jsonToNode(String json) {
        JsonNode root = null;
        try {
            root = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private static String get(String urlToGet) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "apikey token=" + API_KEY);
            conn.setRequestProperty("Accept", "application/json");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

