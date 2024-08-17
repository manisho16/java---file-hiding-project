package model;

import javax.xml.crypto.Data;

public class data implements Data {
    private int id;
    private String FILE_NAME;
    private String path;
    private String email;

    public data(int id, String FILE_NAME, String path) {
        this.id = id;
        this.FILE_NAME = FILE_NAME;
        this.path = path;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }
    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public data(int id, String FILE_NAME, String path, String email) {
        this.id = id;
        this.FILE_NAME = FILE_NAME;
        this.path = path;
        this.email = email;
    }
}
