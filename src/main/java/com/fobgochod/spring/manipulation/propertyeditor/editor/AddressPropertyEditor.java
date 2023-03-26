package com.fobgochod.spring.manipulation.propertyeditor.editor;

import java.beans.PropertyEditorSupport;

public class AddressPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] arrays = text.split("_");
        Address address = new Address();
        address.setProvince(arrays[0]);
        address.setCity(arrays[1]);
        address.setTown(arrays[2]);
        setValue(address);
    }
}
