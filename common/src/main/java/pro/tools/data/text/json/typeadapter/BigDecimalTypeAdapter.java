package pro.tools.data.text.json.typeadapter;

import org.google.gson.stream.JsonReader;
import org.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalTypeAdapter extends ABasicTypeAdapter<BigDecimal> {

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
