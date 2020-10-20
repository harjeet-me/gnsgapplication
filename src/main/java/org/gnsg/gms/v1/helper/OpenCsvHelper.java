package org.gnsg.gms.v1.helper;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class OpenCsvHelper {

    public static <T> String ListJson(List<T> allRevenue) {
        try {
            // create a write

            FileOutputStream fos = new FileOutputStream("newfile.txt");
            Writer writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            // Writer writer = new OutputStreamWriter(out, enc);

            // create a csv writer
            StatefulBeanToCsv<T> csvWriter = new StatefulBeanToCsvBuilder<T>(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

            // write list of objects
            csvWriter.write(allRevenue);

            // close the writer
            writer.close();
            //    FileInputStream fis =  new FileInputStream("newfile.txt");
            return FileUtils.readFileToString(new File("newfile.txt"), StandardCharsets.UTF_8);
            ///  return filedata.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
