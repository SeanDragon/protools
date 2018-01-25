package pro.tools.data.text.json.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author SeanDragon
 */
public abstract class BaseTypeAdapter<T> extends TypeAdapter<T> {
    BaseTypeAdapter() {
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

    /**
     * 读取值
     *
     * @param jsonReader
     *         gson的阅读器
     *
     * @return 返回值
     *
     * @throws IOException
     */
    public abstract T reading(JsonReader jsonReader) throws IOException;

    /**
     * 赋值
     *
     * @param jsonWriter
     *         gson的书写器
     * @param value
     *         想要赋上的值
     *
     * @throws IOException
     */
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
