package com.gsaves.media3.gsaves.app;

public class PrefCartData {
    public String key;
    public String value;

    public PrefCartData() {
    }

    public PrefCartData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PrefCartData && key.equals(((PrefCartData) object).key);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
