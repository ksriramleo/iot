package com.iot.dataservice;

import javax.persistence.*;

/**
 * Created by srirkumar on 10/17/2015.
 */
@Entity
@Table(name ="user", schema = "world")
public class Commodity {
    public void setId(Long id) {
        this.id = id;
    }

    @Id
    private Long id;

    public void setEmail(String email) {
        this.email = email;
    }


    private String email;

}
