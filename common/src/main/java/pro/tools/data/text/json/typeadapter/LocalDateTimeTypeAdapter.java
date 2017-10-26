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
        // LocalDate localDate;
        // LocalTime localTime;
        // String dateValue = jsonReader.nextString();
        // localDate = LocalDate.parse(dateValue, DATE_FORMATTER);
        //
        // System.out.println(jsonReader.nextString());
        //
        // String timeValue = jsonReader.nextString();
        // localTime = LocalTime.parse(timeValue, TIME_FORMATTER);
        // return LocalDateTime.of(localDate, localTime);

        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        LocalDateTime dateTime = LocalDateTime.parse(value, DATE_TIME_FORMATTER);
        jsonReader.endObject();
        return dateTime;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        jsonWriter.beginObject().name("$timestamp").value(value.format(DATE_TIME_FORMATTER)).endObject();
    }
}
