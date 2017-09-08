package com.gong3000.user.entity;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "user")
public class User {

    private Long id;
    private String username;
    private String password;
    private String status;
    private Timestamp createDatetime;
    private Timestamp updateDatetime;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username", unique = true, nullable = false, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "status", nullable = false, length = 4)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "create_datetime", nullable = false)
    public Timestamp getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Timestamp createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name = "update_datetime", nullable = false)
    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    @PreUpdate
    private void update() {
        this.updateDatetime = new Timestamp(System.currentTimeMillis());
    }

    @PrePersist
    private void persist() {
        this.createDatetime = new Timestamp(System.currentTimeMillis());
        this.updateDatetime = new Timestamp(System.currentTimeMillis());
    }
}
