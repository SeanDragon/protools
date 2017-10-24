package pro.tools.data.text.json.typeadapter;

import org.google.gson.stream.JsonReader;
import org.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends ABasicTypeAdapter<LocalDateTime> {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSSSSS");

    @Override
    public LocalDateTime reading(JsonReader jsonReader) throws IOException {
        LocalDateTime dateTime = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString().replace(":", ".");
        String dateStr = value.substring(0, value.lastIndexOf(46));
        String incStr = value.substring(value.lastIndexOf(46) + 1);
        String micro = String.format("%06d", Integer.valueOf(incStr));
        String dvalue = dateStr + "." + micro;
        dateTime = LocalDateTime.parse(dvalue, DATE_TIME_FORMATTER);
        jsonReader.endObject();
        return dateTime;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        jsonWriter.beginObject().name("$timestamp").value(value.format(DATE_TIME_FORMATTER)).endObject();
    }
}
