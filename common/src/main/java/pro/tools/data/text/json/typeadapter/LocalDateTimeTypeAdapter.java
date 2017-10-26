package pro.tools.data.text.json.typeadapter;

import org.google.gson.stream.JsonReader;
import org.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends ABasicTypeAdapter<LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public LocalDateTime reading(JsonReader jsonReader) throws IOException {
        LocalDateTime localDateTime;
        String value = jsonReader.nextString();
        localDateTime = LocalDateTime.parse(value, DATE_TIME_FORMATTER);
        return localDateTime;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        jsonWriter.jsonValue(value.format(DATE_TIME_FORMATTER));
    }
}
