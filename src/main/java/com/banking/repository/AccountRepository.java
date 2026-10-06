import com.banking.model.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class AccountRepository {

    private final Map<Long, Account> accounts = new HashMap<>();


    //add account to map
    public void save(Account account){
        if(account == null){
            throw new IllegalArgumentException("com.banking.model.Account cannot be null");
        }

        if(accounts.containsKey(account.getId())){
            throw new IllegalArgumentException("This account already exists");
        }

        accounts.put(account.getId(), account);
    }


    //find account by id
    public Optional<Account> findById(long id){
        return Optional.ofNullable(accounts.get(id));
    }


    //get all accounts holding by one user
    public List<Account> findByUserId(long userId){
        List<Account> userAccounts = new ArrayList<>();

        for(Account account : accounts.values()){
            if(account.getUserId() == userId){
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }


    //get all accounts
    public List<Account> findAll(){
        return new ArrayList<>(accounts.values());
    }


    //delete account by id
    public void deleteById(long id){
        if (accounts.remove(id) == null) {
            throw new AccountNotFoundException("com.banking.model.Account not found");
        }

    }

}
