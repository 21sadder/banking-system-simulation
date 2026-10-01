public class User {
    private final long id;
    private String name;
    private String phoneNumber;
    private String email;

    public User(int id, String name, String phoneNumb, String email){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumb;
        this.email = email;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changePhoneNumb(String phoneNumb){
        this.phoneNumber = phoneNumb;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumb() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
