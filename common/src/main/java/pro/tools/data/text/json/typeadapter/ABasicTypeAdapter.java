package pro.tools.data.text.json.typeadapter;

import org.google.gson.TypeAdapter;
import org.google.gson.stream.JsonReader;
import org.google.gson.stream.JsonToken;
import org.google.gson.stream.JsonWriter;

import java.io.IOException;

public abstract class ABasicTypeAdapter<T> extends TypeAdapter<T> {
    public ABasicTypeAdapter() {
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return this.reading(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, T value) throws IOException {
        if (value == null) {
            jsonWriter.nullValue();
        } else {
            this.writing(jsonWriter, value);
        }
    }

    public abstract T reading(JsonReader jsonReader) throws IOException;

    public abstract void writing(JsonWriter jsonWriter, T value) throws IOException;

    protected boolean compareToken(JsonToken currentToken, JsonToken targetToken) {
        return currentToken == targetToken;
    }

    protected boolean isNameToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.NAME);
    }

    protected boolean isStringToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.STRING);
    }

    protected boolean isNumberToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.NUMBER);
    }

    protected boolean isBooleanToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.BOOLEAN);
    }

    protected boolean isBeginToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.BEGIN_OBJECT);
    }

    protected boolean isEndToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.END_OBJECT);
    }

    protected boolean isBeginArrToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.BEGIN_ARRAY);
    }

    protected boolean isEndArrToken(JsonToken currentToken) {
        return this.compareToken(currentToken, JsonToken.END_ARRAY);
    }
}
