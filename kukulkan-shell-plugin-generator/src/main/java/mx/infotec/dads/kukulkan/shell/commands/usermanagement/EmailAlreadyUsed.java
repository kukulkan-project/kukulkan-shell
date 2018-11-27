package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

public class EmailAlreadyUsed extends Throwable {

    /**
     * 
     */
    private static final long serialVersionUID = 2841984760894926382L;

    public EmailAlreadyUsed() {
        super("Email already in use");
    }

}
