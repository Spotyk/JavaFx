package org.meral.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final StringProperty name;

    private final StringProperty sername;

    public User(String name, String sername) {
        this.name = new SimpleStringProperty(name);
        this.sername = new SimpleStringProperty(sername);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSername() {
        return sername.get();
    }

    public void setSername(String sername) {
        this.sername.set(sername);
    }

    public StringProperty sernameProperty() {
        return sername;
    }
}
