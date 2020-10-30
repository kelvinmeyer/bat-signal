package media;

import core.DiscordApiProvider;
import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ServerEmojies {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerEmojies.class);

    private Map<String, String> emojis;
    private Random random;

    @Inject
    DiscordApiProvider api;

    @Lock(LockType.WRITE)
    public void refresh() {
        LOGGER.info("Refreshing server emojis");
        emojis = new ConcurrentHashMap<>();
        Collection<KnownCustomEmoji> data = api.getApi().getCustomEmojis();
        for (KnownCustomEmoji datum : data) {
            emojis.put(datum.getName(), datum.getReactionTag());
        }
        LOGGER.info("Found [{}] emojis on the server", emojis.size());
    }

    @PostConstruct
    protected void setup() {
        random = new Random();
        refresh();
    }

    @Lock(LockType.READ)
    public String randomId() {
        if (emojis.isEmpty()) {
            return "❓";
        }
        List<String> keys = new ArrayList<>(emojis.keySet());
        return emojis.get(keys.get(random.nextInt(keys.size())));
    }
}
