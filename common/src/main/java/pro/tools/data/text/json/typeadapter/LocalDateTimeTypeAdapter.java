package pro.tools.data.text.json.typeadapter;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author SeanDragon
 */
public class LocalDateTimeTypeAdapter extends BaseTypeAdapter<LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public LocalDateTime reading(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        LocalDateTime dateTime = LocalDateTime.parse(value, DATE_TIME_FORMATTER);
        //region 忽略时间戳属性
        if (jsonReader.hasNext()) {
            jsonReader.nextName();
            jsonReader.nextString();
        }
        //endregion
        jsonReader.endObject();
        return dateTime;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        jsonWriter
                .beginObject()
                .name("value")
                .value(value.format(DATE_TIME_FORMATTER))
                .name("timestamp")
                .value(Timestamp.valueOf(value).getTime())
                .endObject();
    }
}
