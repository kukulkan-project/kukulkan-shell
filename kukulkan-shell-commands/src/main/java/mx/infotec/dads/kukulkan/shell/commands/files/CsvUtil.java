package mx.infotec.dads.kukulkan.shell.commands.files;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * CsvUtil
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class CsvUtil {

    public static <T> List<T> loadCsvAsList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues = mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<String[]> loadManyToManyRelationship(String fileName) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<String[]> readValues = mapper.reader(String[].class).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
}
