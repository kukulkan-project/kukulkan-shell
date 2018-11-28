package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAuthority {

    @JsonProperty("user_id")
    private Long id;

    @JsonProperty("authority_name")
    private Authority authority;

    public UserAuthority() {
    }

    public UserAuthority(Long id, Authority authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

}
