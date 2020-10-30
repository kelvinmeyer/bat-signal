package core;

import org.javacord.api.entity.message.Message;
import persistance.Notifications;
import persistance.NotificationsDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.StringJoiner;

@Stateless
public class UserService {

    @Inject
    private NotificationsDAO notificationsDAO;

    public void notify(Message message) {
        if (message.getServer().isPresent()) {
            List<String> users = notificationsDAO.getSubs(message.getServer().get().getIdAsString());

            StringJoiner joiner = new StringJoiner(" ");
            for (String user : users) {
                joiner.add("<@"+user+">");
            }

            message.getChannel().sendMessage("Yo " + joiner.toString());
        }
    }

    public void subscribe(Message message) {
        if (message.getServer().isPresent()) {
            String userId = message.getAuthor().getIdAsString();
            String guildId = message.getServer().get().getIdAsString();

            Notifications entity = notificationsDAO.read(userId, guildId);

            if (entity != null) {
                entity.setUserName(message.getAuthor().getDisplayName());
                notificationsDAO.update(entity);
            } else {
                entity = new Notifications();
                entity.setUserName(message.getAuthor().getDisplayName());
                entity.setUserId(userId);
                entity.setGuildId(guildId);
                notificationsDAO.create(entity);
            }
        }
    }

    public void unSubscribe(Message message) {
        if (message.getServer().isPresent()) {
            String userId = message.getAuthor().getIdAsString();
            String guildId = message.getServer().get().getIdAsString();
            notificationsDAO.delete(userId, guildId);
        }
    }
}
