package listeners;

import core.DiscordApiProvider;
import core.UserService;
import media.ServerEmojies;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

// TODO: add the special types so only relavant messages gets injected
public class Console {
    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    @Inject
    private ServerEmojies emojiProvider;

    @Inject
    private DiscordApiProvider api;

    @Inject
    private UserService userService;

    public void commands(@ObservesAsync MessageCreateEvent message) {
        if (message.getMessageContent().startsWith("[]")) {
            String command = message.getMessageContent().replace("[]", " ").trim();
            switch (command) {
                case "reload emoji":
                    emojiProvider.refresh();
                    break;
                case "s":
                    subscribe(message);
                    break;
                case "n":
                    userService.notify(message.getMessage());
                    break;
                case "music join":
                    // todo: join the current spotify session
                    break;
                case "help":
                    // todo: print help
                    break;
                default:
                    LOGGER.warn("Unknown command [{}]", command);
            }
        }
    }

    private void subscribe(MessageCreateEvent message) {
        userService.subscribe(message.getMessage());
    }

//    public void copyPasta(@ObservesAsync MessageCreateEvent message) {
//        List<User> users = message.getMessage().getMentionedUsers();
//        for (User user : users) {
//            if (user.isYourself()) {
////                if (sendMessage(message, "Gentlemen \uD83E\uDDD1\uD83D\uDC68\uD83D\uDC71\uD83E\uDDD4\uD83D\uDC74, a short view ☀️\uD83D\uDE0E back to the past. Thirty years 3️⃣0️⃣ ago, Niki Lauda \uD83C\uDDE6\uD83C\uDDF9\uD83D\uDC74\uD83C\uDFC1\uD83C\uDFCE️3️⃣\uD83C\uDFC6 told us: “Take a trained monkey \uD83C\uDF4C\uD83D\uDC12, place him into the cockpit \uD83D\uDCBA☸️ and he is able to drive the car \uD83C\uDFCE️\uD83C\uDFCE️\uD83C\uDFCE️.” Thirty years 3️⃣0️⃣ later, Sebastian \uD83C\uDDE9\uD83C\uDDEA\uD83D\uDC71\uD83C\uDFC1\uD83C\uDFCE️4️⃣\uD83C\uDFC6 told us: “I had to start \uD83D\uDD11 my car \uD83C\uDFCE️ like a computer \uD83D\uDDA5️\uD83D\uDCBB⌨️\uD83D\uDDB1️\uD83D\uDDA8️\uD83D\uDCBD\uD83D\uDCBE\uD83D\uDCBF\uD83D\uDCC0. It’s very complicated ❓\uD83E\uDD14❓.” And Nico Rosberg \uD83C\uDDE9\uD83C\uDDEA\uD83D\uDC71\uD83C\uDFC1\uD83C\uDFCE️1️⃣\uD83C\uDFC6 said, err, he pressed during the race \uD83C\uDFC1\uD83C\uDFCE️, I don’t remember \uD83D\uDE15 what race \uD83C\uDFC1\uD83C\uDFCE️, the wrong ✖️ button \uD83D\uDD18\uD83D\uDE31 on the wheel ☸️. Question ❓❔❓❔ for you two \uD83D\uDC71\uD83D\uDC68 both. Is Formula 1 1️⃣\uD83C\uDFC6\uD83C\uDF7E driving today too complicated \uD83E\uDD37\u200D♀️\uD83E\uDD37\u200D♂️ with 20 2️⃣0️⃣ and more ➕ buttons \uD83D\uDD18\uD83D\uDD18\uD83D\uDD18 on the wheel ☸️, are you too much under effort \uD83D\uDE30\uD83D\uDE25\uD83D\uDE30\uD83D\uDE25, under pressure \uD83C\uDFCB️\uD83C\uDFCB️\u200D♀️? What are your wishes \uD83D\uDE4F\uD83D\uDE4C\uD83D\uDE4F for the future, concerning technical program, errrm, during the race \uD83C\uDFC1\uD83C\uDFCE️? Less ➖ buttons \uD83D\uDD18, more? Or less ➖ and more ➕communication with your engineers. \uD83C\uDFCE️\uD83D\uDCE3\uD83D\uDDE3️\uD83D\uDCAC\uD83D\uDDEF️\uD83D\uDCC8\uD83D\uDCC9\uD83D\uDCCA\uD83D\uDCCB➡️\uD83D\uDC69\u200D\uD83D\uDCBB\uD83D\uDC68\u200D\uD83D\uDCBB\uD83D\uDC69\u200D\uD83D\uDCBB\uD83D\uDC68\u200D\uD83D\uDCBB\uD83D\uDEE0️\uD83D\uDD27"))
//                break;
//            }
//        }
//    }

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
