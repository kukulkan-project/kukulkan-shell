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
package mx.infotec.dads.kukulkan.shell.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.infotec.dads.kukulkan.metamodel.foundation.Database;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.PKGenerationStrategy;
import mx.infotec.dads.kukulkan.shell.domain.KukulkanShellContext;

/**
 * The Class ProjectContextConfiguration.
 */
@Configuration
public class ProjectContextConfiguration {

    /**
     * Config project context.
     *
     * @return the project configuration
     */
    @Bean
    public KukulkanShellContext configProjectContext() {
        ProjectConfiguration pConf = new ProjectConfiguration();
        pConf.setId("default");
        pConf.setVersion("1.0.0");
        pConf.setPackaging("mx.infotec.dads.default");
        pConf.setYear("2018");
        pConf.setAuthor("KUKULKAN");
        pConf.setDatabase(new Database(DatabaseType.SQL_MYSQL, PKGenerationStrategy.AUTO));
        return new KukulkanShellContext(Optional.of(pConf));
    }
}