package pro.tools.data.text.json.typeadapter;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import pro.tools.time.DatePlus;
import pro.tools.time.ToolDatePlus;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Created on 17/10/26 22:20 星期四.
 *
 * @author sd
 */
public class DatePlusTypeAdapter extends BaseTypeAdapter<DatePlus> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public DatePlus reading(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        DatePlus datePlus = new DatePlus(value, DATE_TIME_FORMATTER);
        //region 忽略时间戳属性
        if (jsonReader.hasNext()) {
            jsonReader.nextName();
            jsonReader.nextString();
        }
        //endregion
        jsonReader.endObject();
        return datePlus;
    }

    @Override
    public void writing(JsonWriter jsonWriter, DatePlus value) throws IOException {
        jsonWriter
                .beginObject()
                .name("value")
                .value(value.toString(DATE_TIME_FORMATTER))
                .name("timestamp")
                .value(ToolDatePlus.localDateTime2time(value.getLocalDateTime()))
                .endObject();
    }
}
