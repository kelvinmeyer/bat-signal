package persistance;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notifications {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private Date created;

    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    private Date updated;

    private String userName;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "guild_id")
    private String guildId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }
}
