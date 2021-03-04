package account;

import api.AccountApi;
import entity.Account;
import utils.LogUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoutzzz
 */
public class AccountService implements AccountApi {


    private static final int ACCOUNT_MAP_INIT_CAPACITY = 16;
    private static final Map<String, Account> ACCOUNT_MAP = new ConcurrentHashMap<>(ACCOUNT_MAP_INIT_CAPACITY);
    private static final Map<String, Account> ACCOUNT_USER_MAP = new ConcurrentHashMap<>(ACCOUNT_MAP_INIT_CAPACITY);

    @Override
    public Account createAccount(String idCard) {
        if (ACCOUNT_MAP.containsKey(idCard)) {
            LogUtil.log("this idCard has been create account, idCard: " + idCard);
            return null;
        } else {
            Account account = new Account(idCard);
            ACCOUNT_MAP.put(idCard, account);
            ACCOUNT_USER_MAP.put(account.getUsername(), account);
            return account;
        }
    }

    @Override
    public Account getAccount(String idCardOrUsername) {
        if (ACCOUNT_MAP.containsKey(idCardOrUsername)) {
            return ACCOUNT_MAP.get(idCardOrUsername);
        } else {
            return ACCOUNT_USER_MAP.getOrDefault(idCardOrUsername, null);
        }
    }
}
