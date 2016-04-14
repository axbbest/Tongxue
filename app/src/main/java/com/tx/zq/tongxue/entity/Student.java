package com.tx.zq.tongxue.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/3/11.
 */
@Table(name = "student_info")
public class Student {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;


    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
