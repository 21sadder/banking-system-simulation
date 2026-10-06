import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class User {
    private final long id;
    private String name;
    private String surname;
    private String username;
    private String phoneNumber;
    private String email;

    public User(
            long id,
            String name,
            String surname,
            String username,
            String phoneNumber,
            String email
    ){
        validateName(name);
        validateName(surname);
        validateUsername(username);
        validateEmail(email);
        validatePhoneNumber(phoneNumber);

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    private void validateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if(name.length() > 50){
            throw new IllegalArgumentException("Name cannot be more than 50 characters length");
        }
    }


    private void validateUsername(String username){
        if (username == null || !username.matches("^[a-zA-Z0-9_]{3,20}$")) { //is username length > 3 and < 20 symbols
            throw new IllegalArgumentException("Invalid username");
        }
    }


    private void validateEmail(String email){
        if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) { //is email looks like example@text.text
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

    private void validatePhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isBlank()){
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try{
            PhoneNumber number = phoneUtil.parse(phoneNumber, "SK");
            if(!phoneUtil.isValidNumber(number)) {
                throw new IllegalArgumentException("Invalid phone number");
            }
        } catch(NumberParseException e) {
            throw new IllegalArgumentException("Invalid phone number");
        }
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

    public String getUsername(){
        return username;
    }

    public String getSurname(){
        return surname;
    }
}
