package mx.infotec.dads.kukulkan.shell.commands.kukulkan;

public enum Profile {

    DEV("dev"), PROD("prod");

    private String profileName;

    private Profile(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileName() {
        return profileName;
    }
}
