package pro.tools.data.text.json.typeadapter;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import pro.tools.data.decimal.Decimal;

import java.io.IOException;

/**
 * @author SeanDragon
 */
public class DecimalTypeAdapter extends BaseTypeAdapter<Decimal> {

    @Override
    public Decimal reading(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        Decimal decimal = new Decimal(value);
        jsonReader.nextName();
        jsonReader.nextString();
        jsonReader.nextName();
        jsonReader.nextString();
        jsonReader.endObject();
        return decimal;
    }

    @Override
    public void writing(JsonWriter jsonWriter, Decimal value) throws IOException {
        jsonWriter.beginObject()
                .name("fullValue").value(value.fullStrValue())
                .name("doubleValue").value(value.doubleValue())
                .name("moneyValue").value(value.moneyValue())
                .endObject();
    }
}
