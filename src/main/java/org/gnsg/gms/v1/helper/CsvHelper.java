package org.gnsg.gms.v1.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gnsg.gms.domain.Revenue;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CsvHelper {

    public static <T> String ListJson(List<T> allRevenue) {
        //	ListAsAJson(allRevenue);
        JSONObject output;
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            // Gson gson = gsonBuilder.create();

            Gson gson = gsonBuilder
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
            Map<String, Object> revObj = new HashMap<>();
            revObj.put("infile", allRevenue);
            String JSONObject = gson.toJson(revObj);

            System.out.print("Jsonn object ??????????????????" + JSONObject);
            //  output = new JSONObject(ListAsJson(allRevenue));

            output = new JSONObject(JSONObject);

            System.out.print("Jsonn docs ??????????????????   output  \n " + output);
            JSONArray docs = output.getJSONArray("infile");

            System.out.print("Jsonn docs ??????????????????   array  \n " + docs);

            File file = new File("/tmp2/fromJSON.csv");
            String csv = CDL.toString(docs);

            System.out.print("Jsonn docs ??????????????????   csv  \n " + csv);

            return csv;
            // FileUtils.writeStringToFile(file, csv);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
         * catch (IOException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         */

        return null;
    }

    public static String ListAsJson(List<Revenue> allRevenue) {
        // Creating Object of ObjectMapper define in Jakson Api
        ObjectMapper Obj = new ObjectMapper();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Obj.setDateFormat(df);

        try {
            // get Oraganisation object as a json string

            Map<String, Object> revObj = new HashMap<>();
            revObj.put("infile", allRevenue);
            String jsonStr = Obj.writeValueAsString(revObj);

            // Displaying JSON String
            System.out.println(jsonStr);

            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * public static String ListAsAJson(List<Revenue> allRevenue) { // create mapper
     * and schema CsvMapper mapper = new CsvMapper(); CsvSchema schema =
     * mapper.schemaFor(Revenue.class); schema = schema.withColumnSeparator('\t');
     *
     * // output writer ObjectWriter myObjectWriter = mapper.writer(schema); File
     * tempFile = new File("users.csv"); FileOutputStream tempFileOutputStream; try
     * { tempFileOutputStream = new FileOutputStream(tempFile); BufferedOutputStream
     * bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
     * OutputStreamWriter writerOutputStream = new
     * OutputStreamWriter(bufferedOutputStream, "UTF-8");
     * myObjectWriter.writeValue(writerOutputStream, allRevenue); } catch (Exception
     * e) { e.printStackTrace(); }
     *
     * return null; }
     */
}

class LocalDateAdapter implements JsonSerializer<LocalDate> {

    public JsonElement serialize(LocalDate date, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))); // "yyyy-mm-dd"
    }
}

class LocalDateSerializer extends StdSerializer<LocalDate> {

    public LocalDateSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}

class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return LocalDate.parse(parser.readValueAs(String.class));
    }
}
