package com.sgr.dyg.test.bean;

import java.io.Serializable;

public class entities implements Serializable {
    String entity_name,create_time,modify_time;
    latest_location latest_location;

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public latest_location getLatest_location() {
        return latest_location;
    }

    public void setLatest_location(latest_location latest_location) {
        this.latest_location = latest_location;
    }

    @Override
    public String toString() {
        return "entities{" +
                "entity_name='" + entity_name + '\'' +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                ", latest_location=" + latest_location +
                '}';
    }
}
