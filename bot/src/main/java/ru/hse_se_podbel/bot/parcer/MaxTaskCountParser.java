package ru.hse_se_podbel.bot.parcer;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.data.SubjectGroup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class MaxTaskCountParser extends Parser {

    @Getter
    String pathSuffix = "/max-task-count";
    public int maxTaskCount(SubjectGroup subjectGroup) throws URISyntaxException, IOException, InterruptedException {

        String url = getUrl() + "?module=" + subjectGroup.getModule();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        JsonNode node = doRequest(request);
        return Integer.min(node.get("count").asInt(), 40);
    }
}
