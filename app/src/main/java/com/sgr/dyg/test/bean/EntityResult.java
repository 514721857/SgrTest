package com.sgr.dyg.test.bean;

import java.util.List;

public class EntityResult {
   int status;
   String message;
   int size,total;
   List<entities> entities;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<entities> getEntities() {
        return entities;
    }

    public void setEntities(List<entities> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "EntityResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", size=" + size +
                ", total=" + total +
                ", entities=" + entities +
                '}';
    }
}
