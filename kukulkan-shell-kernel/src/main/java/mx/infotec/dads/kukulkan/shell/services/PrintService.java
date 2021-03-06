package mx.infotec.dads.kukulkan.shell.services;

import org.jline.utils.AttributedString;

import mx.infotec.dads.kukulkan.engine.service.GeneratorPrintProvider;

/**
 * Printer Service
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface PrintService extends GeneratorPrintProvider {

    /**
     * Printf.
     *
     * @param text
     *            the text
     */
    public void print(AttributedString message);

    /**
     * Printf.
     *
     * @param key
     *            the key
     * @param message
     *            the message
     */
    public void print(String key, String message);

    /**
     * info message.
     *
     * @param text
     *            the text
     */
    public void info(String message);

    /**
     * Warning message.
     *
     * @param text
     *            the text
     */
    public void warning(String message);

    /**
     * Error message.
     *
     * @param text
     *            the text
     */
    public void error(String message);

}
