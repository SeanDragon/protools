package pro.tools.data.text.json.typeadapter;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author SeanDragon
 */
public class LocalDateTypeAdapter extends BaseTypeAdapter<LocalDate> {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate reading(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        LocalDate localDate = LocalDate.parse(value, DATE_FORMATTER);
        //region 忽略时间戳属性
        if (jsonReader.hasNext()) {
            jsonReader.nextName();
            jsonReader.nextString();
        }
        //endregion
        jsonReader.endObject();
        return localDate;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDate value) throws IOException {
        jsonWriter
                .beginObject()
                .name("value")
                .value(value.format(DATE_FORMATTER))
                .name("timestamp")
                .value(Date.valueOf(value).getTime())
                .endObject();
    }
}
