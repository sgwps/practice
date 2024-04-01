package ru.hse_se_podbel.bot.parcer;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.data.SubjectGroup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TaskSequenceParser extends Parser {
    @Getter
    String pathSuffix = "/task-sequence";
    public List<UUID> getTasks(SubjectGroup group, int count) throws URISyntaxException, IOException, InterruptedException {

        String url = getUrl() + "?module=" + group.getModule() + "&count=" + Integer.toString(count);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        JsonNode node = doRequest(request);
        List<UUID> result = new ArrayList<>();
        node.get("tasks").elements().forEachRemaining(elem -> result.add(UUID.fromString(elem.asText())));
        return result;
    }

}
