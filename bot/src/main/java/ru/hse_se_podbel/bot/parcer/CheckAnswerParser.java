package ru.hse_se_podbel.bot.parcer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class CheckAnswerParser extends Parser{
    @Getter
    String pathSuffix = "/check";

    public boolean checkAnswer(List<String> options, UUID taskId) throws URISyntaxException, IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.set("options", mapper.valueToTree(options));
        node.set("task", mapper.valueToTree(taskId));

        String url = getUrl();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(node.toString()))
                .header("Content-Type", "application/json")
                .build();

        return doRequest(request).get("result").asBoolean();
    }

    public boolean checkAnswer(String answer, UUID taskId) throws URISyntaxException, IOException, InterruptedException {
        return checkAnswer(Collections.singletonList(answer), taskId);
    }
}
