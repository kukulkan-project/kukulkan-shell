package mx.infotec.dads.kukulkan.shell.domain;

import java.util.SortedMap;

/**
 * ProjectContext, It has the main configuration of the Shell
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class NativeCommandContext {

    private SortedMap<String, NativeCommand> availableCommands;

    public SortedMap<String, NativeCommand> getAvailableCommands() {
        return availableCommands;
    }

    public void setAvailableCommands(SortedMap<String, NativeCommand> availableCommands) {
        this.availableCommands = availableCommands;
    }
}
