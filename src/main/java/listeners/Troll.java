package listeners;

import media.ServerEmojies;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

public class Troll {
    private static final Logger LOGGER = LoggerFactory.getLogger(Troll.class);

    @Inject
    ServerEmojies emojiProvider;

    public void react(@ObservesAsync MessageCreateEvent message) {
        LOGGER.info("[{}] said [{}]", message.getMessageAuthor().getDisplayName(), message.getMessageContent());
        try {
            message.addReactionsToMessage(emojiProvider.randomId()).join();
        } catch (Exception e) {
            LOGGER.error("error: [{}]", e.getMessage());
        }
    }

    public void questionTroll(@ObservesAsync MessageCreateEvent message) {
        if (message.getMessageContent().trim().endsWith("?")) {
            sendMessage(message, "Your question is very important to us, we will be with you shortly");
        }
    }

    private boolean sendMessage(MessageCreateEvent message, String messageText) {
        try {
            message.getChannel().sendMessage(messageText).join();
            return true;
        } catch (Exception e) {
            LOGGER.error("Error: [{}]", e.getMessage());
        }
        return false;
    }
}
