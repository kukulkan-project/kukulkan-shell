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
package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseSource;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStore;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStoreType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.TableTypes;
import mx.infotec.dads.kukulkan.shell.prompt.InputShell;
import mx.infotec.dads.kukulkan.shell.util.ShellException;

/**
 * Generator Command.
 *
 * @author Daniel Cortes Pichardo
 */
@Component
public class AppInput {

    private final static String MYSQL_DEFAULT_URL = "jdbc:mysql://localhost:3306";
    private final static String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final static String H2_DEFAULT_URL = "jdbc:h2:~";
    private final static String H2_DRIVER_CLASS = "org.h2.Driver";
    private final static String ORACLE_DEFAULT_URL = "jdbc:oracle:thin:@localhost:1521";
    private final static String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";

    @Autowired
    private InputShell inputShell;

    public DataBaseSource readDataStore(DatabaseType source) {
        DataStore testDataStore = new DataStore();
        testDataStore.setUsername(inputShell.readOption("Username[root] ", "root", true));
        testDataStore.setPassword(inputShell.readPassword("Password ", "", true));
        testDataStore.setUrl(inputShell.readOption("url[" + getDefaultUrl(source) + "] ", getDefaultUrl(source), true));
        testDataStore.setDataStoreType(DataStoreType.SQL);
        testDataStore.setDriverClass(getDriverClass(source));
        String schemaName = inputShell.readOption("Schema Name ", "", true);
        testDataStore.setName(schemaName);
        testDataStore.setTableTypes(TableTypes.TABLE_VIEW);
        testDataStore.setSchema(schemaName);
        return new DataBaseSource(testDataStore);
    }

    private static String getDefaultUrl(DatabaseType source) {
        switch (source) {
        case SQL_MYSQL:
            return MYSQL_DEFAULT_URL;
        case SQL_ORACLE:
            return ORACLE_DEFAULT_URL;
        default:
            throw new ShellException("No Default Url for:" + source);
        }
    }

    private static String getDriverClass(DatabaseType source) {
        switch (source) {
        case SQL_MYSQL:
            return MYSQL_DRIVER_CLASS;
        case SQL_ORACLE:
            return ORACLE_DRIVER_CLASS;
        default:
            throw new ShellException("No Default Url for:" + source);
        }
    }
}
