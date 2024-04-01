package ru.hse_se_podbel.bot.data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.hse_se_podbel.bot.data.Session;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String> {
    public Session findByChatId(String chatId);
    public List<Session> findByLastPollId(String pollId);
}
