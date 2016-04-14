package com.tx.zq.tongxue.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/12.
 */
@Table(name = "peoples")
public class People implements Serializable {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private String age;
    @Column(name = "date")
    private String date;
    @Column(name = "addr")
    private String addr;
    @Column(name = "constellation")
    private String constellation;//星座
    @Column(name = "tel")
    private String tel;
    @Column(name = "hobby")
    private String hobby;//爱好
    @Column(name = "color")
    private String color;
    @Column(name = "skill")
    private String skill;//技能
    @Column(name = "Content")
    private String Content;
    @Column(name = "time")
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public People() {
    }

    public People(String name, String sex, String path, String age, String date, String addr,
                  String constellation, String tel, String hobby, String skill, String content) {
        this.path = path;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.date = date;
        this.addr = addr;
        this.constellation = constellation;
        this.tel = tel;
        this.hobby = hobby;
        this.skill = skill;
        this.time = System.currentTimeMillis();
        Content = content;
    }
}
