package ru.ckassa.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private DateTimeFormatter dateFormat;

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDate != null) {
            jsonGenerator.writeString(localDate.format(dateFormat));
        }

    }

    public LocalDateSerializer(DateTimeFormatter dateFormat){
        super();
        this.dateFormat = dateFormat;
    }
}
