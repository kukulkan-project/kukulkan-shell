package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

public class LoginAlreadyUsed extends Throwable {

    /**
     * 
     */
    private static final long serialVersionUID = 2551759993833732190L;

    public LoginAlreadyUsed() {
        super("Login already in use");
    }

}
