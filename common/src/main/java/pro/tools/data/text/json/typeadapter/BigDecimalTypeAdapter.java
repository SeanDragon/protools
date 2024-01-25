package pro.tools.data.text.json.typeadapter;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author SeanDragon
 */
public class BigDecimalTypeAdapter extends BaseTypeAdapter<BigDecimal> {

    @Override
    public BigDecimal reading(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        BigDecimal decimal = new BigDecimal(jsonReader.nextString());
        jsonReader.endObject();
        return decimal;
    }

    @Override
    public void writing(JsonWriter jsonWriter, BigDecimal value) throws IOException {
        jsonWriter.beginObject().name("value").value(value.toString()).endObject();
    }
}
