package mx.infotec.dads.kukulkan.shell.util;

/**
 * StringBuilderCollector
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class StringBuilderCollector implements BufferCollector<String> {

    StringBuilder output = new StringBuilder();

    @Override
    public void collect(String toCollect) {
        output.append(toCollect).append("\n");
    }

    @Override
    public String getCollection() {
        return output.toString();
    }
}
