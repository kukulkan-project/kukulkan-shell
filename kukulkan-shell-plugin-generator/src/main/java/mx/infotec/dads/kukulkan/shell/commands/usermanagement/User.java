package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "id", "login", "password_hash", "first_name", "last_name", "email", "image_url", "activated",
        "lang_key", "created_by", "last_modified_by" })
public class User {

    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    private Long id;

    @NotNull
    @Pattern(regexp = LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = 60, max = 60)
    @JsonProperty(value = "password_hash")
    private String password;

    @Size(max = 50)
    @JsonProperty(value = "first_name")
    private String firstName;

    @Size(max = 50)
    @JsonProperty(value = "last_name")
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    private boolean activated = true;

    @Size(min = 2, max = 6)
    @JsonProperty(value = "lang_key")
    private String langKey;

    @Size(max = 256)
    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "created_by")
    private String createdBy = "kukulkan";

    @JsonProperty(value = "last_modified_by")
    private String lastModifiedBy = "kukulkan";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", activated=" + activated + ", langKey=" + langKey
                + ", imageUrl=" + imageUrl + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + "]";
    }

}
