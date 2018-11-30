package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.infotec.dads.kukulkan.engine.service.FileUtil;

public class CsvUtils {

	private static final Logger logger = LoggerFactory.getLogger(CsvUtils.class);

	private static final String SEPARATOR = ";";

	private static final ObjectMapper mapper = new ObjectMapper();

	private CsvUtils() {
	}

	/**
	 * Parse csv lines to a List of Map's
	 * 
	 * @param csvLinesWithHeadersAtFirst
	 * @return the list
	 */
	public static List<Map<String, String>> parseCsv(List<String> csvLinesWithHeadersAtFirst) {
		if (csvLinesWithHeadersAtFirst.isEmpty())
			return Collections.emptyList();

		List<Map<String, String>> maps = new ArrayList<>();

		String[] headers = csvLinesWithHeadersAtFirst.get(0).split(SEPARATOR);
		csvLinesWithHeadersAtFirst.remove(0);

		for (String line : csvLinesWithHeadersAtFirst) {
			maps.add(valuesToMap(headers, line.split(SEPARATOR)));
		}

		return maps;
	}

	public static <T> void saveCsv(List<T> objects, Class<T> clazz, File file) {
		try (FileWriter fw = new FileWriter(file);) {
			if (objects.isEmpty()) {
				logger.debug("No content to save to " + file.getAbsolutePath());
			} else {
				PropertyScanner propScanner = new JacksonPropertyScanner(mapper);
				List<Property> props = propScanner.getProperties(clazz);
				List<String> lines = new ArrayList<>();

				// Writing headers
				lines.add(props.stream().map(Property::getName).collect(Collectors.joining(SEPARATOR)));

				// Writing values
				for (Object obj : objects) {
					String csvString = objectToCsvString(getGetterMethods(props), obj, clazz);
					lines.add(csvString);
				}
				FileUtil.saveToFile(file.toPath(), lines);
			}

		} catch (IOException ex) {
			logger.error("Error while writing file " + file.getAbsolutePath());
		}

	}

	private static List<Method> getGetterMethods(List<Property> props) {
		List<Method> methods = new ArrayList<>();
		for (Property prop : props) {
			methods.add(prop.getGetter());
		}
		return methods;
	}

	private static <T> String objectToCsvString(List<Method> getterMethods, Object obj, Class<T> clazz) {
		T casted = (T) obj;
		List<String> values = new ArrayList<>();
		for (Method method : getterMethods) {
			try {
				Object getterResult = method.invoke(casted, null);
				values.add(getterResult.toString());
			} catch (Exception ex) {
				values.add("");
			}
		}
		return values.stream().collect(Collectors.joining(SEPARATOR));
	}

	/**
	 * Map headers and values to a map
	 * 
	 * @param headers
	 * @param values
	 * @return the map
	 */
	private static Map<String, String> valuesToMap(String[] headers, String[] values) {
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < headers.length; i++) {
			map.put(headers[i], values[i]);
		}
		return map;
	}

}
