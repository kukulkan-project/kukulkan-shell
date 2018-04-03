package mx.infotec.dads.kukulkan.shell.util;

/**
 * BufferCollector
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface BufferCollector<L> {

    void collect(String toCollect);

    L getCollection();

}