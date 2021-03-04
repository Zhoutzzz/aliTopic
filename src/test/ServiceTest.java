package test;

import account.AccountService;
import entity.Account;
import transcation.TransferService;

import java.math.BigDecimal;

/**
 * @author zhoutzzz
 */
public class ServiceTest {

    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        TransferService transferService = new TransferService(accountService);
        Account account1 = accountService.createAccount("530100199909290899");
        Account account2 = accountService.createAccount("530134198803270949");
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                transferService.recharge(account1.getUsername(), new BigDecimal("100.00"), "123");
                transferService.recharge(account2.getUsername(), new BigDecimal("100.00"), "123");
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                transferService.transferOut(account1.getUsername(), account2.getUsername(), new BigDecimal("30.00"), "456");
            }
        });
        t1.start();
        t2.start();
    }
}
