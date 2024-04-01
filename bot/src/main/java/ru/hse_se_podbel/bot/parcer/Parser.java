package ru.hse_se_podbel.bot.parcer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class Parser {
    String path = "http://subtz:8000/telegram-api";

    public abstract String getPathSuffix();

    public String getUrl(){
        return path.concat(getPathSuffix());
    }

    public JsonNode doRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(response.body());
        return mapper.readTree(parser);
    }

}
