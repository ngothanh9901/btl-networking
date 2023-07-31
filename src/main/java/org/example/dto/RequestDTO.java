package org.example.dto;

import java.io.Serializable;

public class RequestDTO implements Serializable {
    private String label;
    private Object value;

    public RequestDTO(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
