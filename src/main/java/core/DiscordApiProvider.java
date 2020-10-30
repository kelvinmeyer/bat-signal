package core;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class DiscordApiProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordApiProvider.class);

    private static final String API_KEY = "NzY4MTM4MzcyODM3MTQ2NjI0.X48Gmw.XJz9CpC47otVzIvDDufO1BGCkIQ";
    private DiscordApi api;

    @Inject
    private Event<MessageCreateEvent> commandEvent;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        LOGGER.info("Bot starting and logging into Discord");
        this.api = new DiscordApiBuilder()
                .addMessageCreateListener(event -> {
                    if (!event.getMessageAuthor().isBotUser()) {
                        commandEvent.fireAsync(event);
                    }
                })
                .addServerVoiceChannelMemberJoinListener(e -> {
                    LOGGER.info("hmmm");
                })
                .setToken(API_KEY)
                .login()
                .join();
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
       if (api != null) {
           api.disconnect();
       }
    }

    public DiscordApi getApi() {
        return api;
    }

}
