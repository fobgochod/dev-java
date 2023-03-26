package com.fobgochod.spring.conversion.converterspi;

import com.fobgochod.spring.manipulation.propertyeditor.editor.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter implements Converter<String, Address> {

    @Override
    public Address convert(String source) {
        String[] arrays = source.split("_");
        Address address = new Address();
        address.setProvince(arrays[0]);
        address.setCity(arrays[1]);
        address.setTown(arrays[2]);
        return address;
    }
}
