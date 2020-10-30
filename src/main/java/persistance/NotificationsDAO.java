package persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class NotificationsDAO {

    @PersistenceContext(name = "BatDB")
    private EntityManager em;

    public Notifications read(String userId, String guildId) {
        TypedQuery<Notifications> query = em.createQuery("SELECT n FROM Notifications n " +
                "WHERE n.userId = :userId AND n.guildId = :guildId", Notifications.class);
        query.setParameter("userId", userId);
        query.setParameter("guildId", guildId);
        List<Notifications> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public Notifications create(Notifications entity) {
        em.persist(entity);
        return entity;
    }

    public Notifications update(Notifications entity) {
        entity = em.merge(entity);
        return entity;
    }

    public Notifications delete(String userId, String guildId) {
        Notifications entity = read(userId, guildId);
        if (entity != null) {
            em.remove(entity);
        }
        return entity;
    }

    public List<String> getSubs(String guildId) {
        TypedQuery<String> query = em.createQuery("SELECT n.userId FROM Notifications n " +
                "WHERE n.guildId = :guildId", String.class);
        query.setParameter("guildId", guildId);
        List<String> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return resultList;
        }
    }
}
