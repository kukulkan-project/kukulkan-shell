package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

public class AuthorityHasAssociatedUsers extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -5360453928680871861L;

    public AuthorityHasAssociatedUsers() {
        super("Cannot delete authority because it has associated users");
    }

}
