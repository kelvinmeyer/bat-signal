package listeners;

import core.UserService;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.awt.*;

public class AtMe {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtMe.class);

    @Inject
    private UserService userService;

    public void commands(@ObservesAsync MessageCreateEvent message) {
        if (message.getMessageContent().startsWith("!")) {
            String command = message.getMessageContent().replace("!", " ").trim();
            switch (command.toLowerCase()) {
                case "sub":
                case "subscribe":
                    userService.subscribe(message.getMessage());
                    break;
                case "unsub":
                case "unsubscribe":
                    userService.unSubscribe(message.getMessage());
                    break;
                case "bat signal":
                    userService.notify(message.getMessage());
                    break;
                case "help":
                    help(message.getMessage());
                    break;
                default:
                    LOGGER.warn("Unknown command [{}]", command);
            }
        }
    }

    private void help(Message message) {
        // Create the embed
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Bat Signal help")
                .addInlineField("!sub", "Subscribe to get notifications when the bat signal is turned on")
                .addInlineField("!unsub", "Unsubscribe from notifications when the bat signal is turned on")
                .addInlineField("!bat signal", "turn on the bat signal to call all subscribers")
                .addInlineField("!help", "You are here")
                .setColor(Color.RED);
        message.getChannel().sendMessage(embed);
    }

}
