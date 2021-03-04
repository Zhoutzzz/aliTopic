package api;

import entity.Account;

public interface AccountApi {
    Account createAccount(String idCard);

    Account getAccount(String idCard);
}
