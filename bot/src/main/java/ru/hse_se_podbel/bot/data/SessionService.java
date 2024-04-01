package ru.hse_se_podbel.bot.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse_se_podbel.bot.exception.NotSingleSessionException;
import ru.hse_se_podbel.bot.exception.SessionNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;

    public void createNew(String chatId, SubjectGroup group) {
        Session existing = sessionRepository.findByChatId(chatId);
        if (existing != null) {
            sessionRepository.delete(existing);
        }
        Session session = Session.builder().chatId(chatId).group(group).build();
        sessionRepository.save(session);

    }

    public Session findByChatId(String chatId) throws SessionNotFoundException {

        Session session = sessionRepository.findByChatId(chatId);
        if (session == null) throw new SessionNotFoundException();
        return session;

    }
    public Session findByLastPollId(String pollId) throws NotSingleSessionException {
        List<Session> sessions = sessionRepository.findByLastPollId(pollId);
        if (sessions.size() != 1) throw new NotSingleSessionException();
        return sessions.get(0);
    }




    public Session updateAnswer(String chatId, boolean isCorrect) throws SessionNotFoundException {
        Session session = findByChatId(chatId);
        session.setCurrentTask(session.getCurrentTask() + 1);
        if (isCorrect) {
            session.setRightAnswersCount(session.getRightAnswersCount() + 1);
        }
        return sessionRepository.save(session);
    }

    public Session setTasks(String chatId, List<UUID> tasks) throws SessionNotFoundException {
        Session session = findByChatId(chatId);
        session.setTasks(tasks);
        return sessionRepository.save(session);

    }

    public Session updateLastPollId(String pollId, String chatId) throws SessionNotFoundException {
        Session session = findByChatId(chatId);
        session.setLastPollId(pollId);
        return sessionRepository.save(session);
    }

    public Session updateLastKeyboardMessageId(String messageId, String chatId) throws SessionNotFoundException {
        Session session = findByChatId(chatId);
        session.setLastKeyboardMessageId(messageId);
        return sessionRepository.save(session);
    }

    public void delete(Session session) {
        sessionRepository.delete(session);
    }

}
