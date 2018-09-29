package mx.infotec.dads.kukulkan.shell.commands.files;

import java.io.Serializable;

/**
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class FileNamePair implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
