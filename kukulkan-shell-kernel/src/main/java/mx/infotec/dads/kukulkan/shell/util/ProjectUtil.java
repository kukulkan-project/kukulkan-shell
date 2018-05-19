/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mx.infotec.dads.kukulkan.shell.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mx.infotec.dads.kukulkan.engine.service.GeneratorPrintProvider;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

/**
 * Project Reader.
 *
 * @author Daniel Cortes Pichardo
 */
@Component
public class ProjectUtil {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectUtil.class);

    /** The Constant KUKULKAN_FILE. */
    public static final String KUKULKAN_FILE = ".kukulkan.json";

    @Autowired
    private GeneratorPrintProvider generatorPrintProvider;

    private static GeneratorPrintProvider printProvider;

    @PostConstruct
    public void init() {
        ProjectUtil.printProvider = generatorPrintProvider;
    }

    /**
     * Save kukulkan file.
     *
     * @param config
     *            the config
     */
    public static void writeKukulkanFile(ProjectConfiguration config) {
        try {
            Path kukulkanFilePath = Paths.get(config.getOutputDir().toString(), config.getId(), KUKULKAN_FILE);
            printProvider.info("saveFile to: {}", kukulkanFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(kukulkanFilePath.toFile(), config);
        } catch (IOException e) {
            throw new GeneratorException("The file is no well structure");
        }
    }

    /**
     * Read kukulkan file.
     *
     * @param projectPath
     *            the project path
     * @return the project configuration
     */
    public static Optional<ProjectConfiguration> readKukulkanFile(Path projectPath) {
        try {
            Path kukulkanFilePath = Paths.get(projectPath.toString(), KUKULKAN_FILE);
            ObjectMapper objectMapper = new ObjectMapper();
            Optional<ProjectConfiguration> pConf = Optional
                    .of(objectMapper.readValue(kukulkanFilePath.toFile(), ProjectConfiguration.class));
            pConf.ifPresent(conf -> conf.setOutputDir(projectPath.getParent()));
            return pConf;
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Instantiates a new project util.
     */
    private ProjectUtil() {

    }
}
