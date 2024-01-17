package pro.tools.data.text.json.typeadapter;

import org.google.gson.stream.JsonReader;
import org.google.gson.stream.JsonWriter;
import pro.tools.data.decimal.Decimal;

import java.io.IOException;

public class DecimalTypeAdapter extends ABasicTypeAdapter<Decimal> {

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
