package com.fobgochod.spring.conversion.converterfactory;

import com.fobgochod.spring.manipulation.propertyeditor.editor.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.Nullable;

public class AddressConverterFactory implements ConverterFactory<String, Address> {

    @Override
    public <T extends Address> Converter<String, T> getConverter(Class<T> targetType) {
        return new AddressConverter<>();
    }

    private static final class AddressConverter<T extends Address> implements Converter<String, T> {

        @Override
        @Nullable
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            String[] arrays = source.split("_");
            Address address = new Address();
            address.setProvince(arrays[0]);
            address.setCity(arrays[1]);
            address.setTown(arrays[2]);
            return (T) address;
        }
    }
}
