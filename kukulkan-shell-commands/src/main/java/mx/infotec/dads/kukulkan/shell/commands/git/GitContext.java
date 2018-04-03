package mx.infotec.dads.kukulkan.shell.commands.git;

import org.springframework.stereotype.Component;

import mx.infotec.dads.kukulkan.shell.prompt.Resettable;

/**
 * GitContext, It is the git context used for keep traking of the status of the
 * git command.
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Component
public class GitContext implements Resettable {

    private static final long serialVersionUID = 1L;

    boolean master;
    boolean develop;
    boolean avaliable;

    public boolean isMaster() {
        return master;
    }

    public boolean isDevelop() {
        return develop;
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public void setDevelop(boolean develop) {
        this.develop = develop;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    @Override
    public void reset() {
        this.master = this.develop = this.avaliable = false;
    }

}
