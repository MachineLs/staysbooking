package com.laioffer.staybooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;

@Entity //creat table
@Table(name = "stay_image") //给table 加名字
public class StayImage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id //primary key
    private String url;

    @ManyToOne //一个stay 对应很多 image, 一对多的关系
    @JoinColumn(name = "stay_id") //foreign key的名字,多对一的关系
    @JsonIgnore //无论是stay和image的内容 这条数据stay都不返回,只会返回13行的url
    private Stay stay;

    public StayImage() {}

    public StayImage(String url, Stay stay) {
        this.url = url;
        this.stay = stay;
    }

    public String getUrl() {
        return url;
    }

    public StayImage setUrl(String url) {
        this.url = url;
        return this;
    }

    public Stay getStay() {
        return stay;
    }

    public StayImage setStay(Stay stay) {
        this.stay = stay;
        return this;
    }
}