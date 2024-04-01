package ru.hse_se_podbel.bot.parcer;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.structures.AnswerOption;
import ru.hse_se_podbel.bot.structures.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TaskParser extends Parser {

    @Getter
    String pathSuffix = "/task";

    public Task parse(UUID taskId) throws URISyntaxException, IOException, InterruptedException {
        String url = getUrl() + "?id=" + taskId.toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        JsonNode node = doRequest(request);


        Task task = new Task(); // TODO: ручка!
        task.setQuestion(node.get("question").asText());
        task.setCode(node.get("code").asText());
        task.setId(taskId);
        List<AnswerOption> options = new ArrayList<>();
        if (node.has("options")) {
            node.get("options").elements().forEachRemaining(optionJson -> options.add(new AnswerOption(options.size() + 1, false, optionJson.asText())));
        }
        task.setAnswerOptions(options);

        return task;
    }
}
