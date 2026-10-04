public class User {
    private final long id;
    private String name;
    private String surname;
    private String username;
    private String phoneNumber;
    private String email;

    public User(int id, String name, String surname, String username, String phoneNumb, String email){
        this.id = id;
        isValidUserName(username);
        this.phoneNumber = phoneNumb;
        this.email = email;
    }

    public void isValidUserName(String username){
        if (username == null || !username.matches("^[a-zA-Z0-9_]{3,20}$")) { //is username length > 3 and < 20 symbols
            throw new IllegalArgumentException("Invalid username");
        }
        this.username = username;
    }

    public void isValidEmail(String email){
        if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) { //is email looks like example@text.text
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        this.email = email;
    }

    public void isValidPhoneNumber(String phoneNumb){
        this.phoneNumber = phoneNumb;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
