package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

public class AuthorityNotExists extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1879184439634324905L;

    public AuthorityNotExists(String name) {
        super("Authority does not exists: " + name);
    }

}
