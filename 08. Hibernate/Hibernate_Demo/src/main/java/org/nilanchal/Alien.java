package org.nilanchal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="alien_data")
public class Alien {

    @Id
    private int aid;
    private String uname;
    private String tech;

    public int getAid() {
        return aid;
    }

    public String getTech() {
        return tech;
    }

    public String getUname() {
        return uname;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "aid=" + aid +
                ", uname='" + uname + '\'' +
                ", tech='" + tech + '\'' +
                '}';
    }
}
