package mx.infotec.dads.kukulkan.shell.commands.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.shell.commands.exception.GeneratorException;

/**
 * Project Reader
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class ProjectUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectUtil.class);

    public static final String KUKULKAN_FILE = ".kukulkan.json";

    public static void saveKukulkanFile(ProjectConfiguration config) {
        try {
            Path kukulkanFilePath = Paths.get(config.getOutputDir().toString(), config.getId(), KUKULKAN_FILE);
            LOGGER.info("saveFile to: {}", kukulkanFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(kukulkanFilePath.toFile(), config);
        } catch (IOException e) {
            throw new GeneratorException("The file is no well structure");
        }
    }

    public static ProjectConfiguration readKukulkanFile(ProjectConfiguration config) {
        try {
            Path kukulkanFilePath = Paths.get(config.getOutputDir().toString(), config.getId(), KUKULKAN_FILE);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(kukulkanFilePath.toFile(), ProjectConfiguration.class);
        } catch (IOException e) {
            throw new GeneratorException("The file is no well structure");
        }

    }

    private ProjectUtil() {

    }
}
