package transcation;

import account.AccountService;
import api.TransferOut;
import entity.Account;
import utils.LogUtil;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhoutzzz
 */
public class TransferService implements TransferOut {
    private final AccountService accountService;
    private final ReentrantLock lock = new ReentrantLock();

    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public BigDecimal recharge(String username, BigDecimal rechargeMoney, String requestId) {
        Account rechargeAccount = accountService.getAccount(username);
        Objects.requireNonNull(rechargeAccount, "username error");
        BigDecimal balance = rechargeAccount.recharge(username, rechargeMoney);
        LogUtil.log("recharge success, account: " + rechargeAccount.getIdCard() + " balance: " + balance);
        return balance;
    }

    @Override
    public BigDecimal transferOut(String inTransferUser, String outTransferUser, BigDecimal transferMoney, String requestId) {

        Account inAccount = accountService.getAccount(inTransferUser);
        Objects.requireNonNull(inAccount, "inTransferUser error");
        Account outAccount = accountService.getAccount(outTransferUser);
        Objects.requireNonNull(outAccount, "outTransferUser error");
        BigDecimal outAccountBalance;
        boolean getLock = false;
        try {
            getLock = lock.tryLock(3, TimeUnit.SECONDS);
            if (getLock) {
                outAccountBalance = outAccount.transferAmount(outTransferUser, transferMoney);
                inAccount.recharge(inTransferUser, transferMoney);
                LogUtil.log("transfer out success, account: " + outAccount.getIdCard() + ", balance: " + outAccountBalance.toEngineeringString());
            } else {
                outAccountBalance = outAccount.getAmount();
                LogUtil.log("transfer out timeout, account: " + outAccount.getIdCard() + ", balance: " + outAccountBalance.toEngineeringString());
            }
        } catch (InterruptedException e) {
            LogUtil.log("transfer out failed, account: " + outAccount.getIdCard());
            outAccountBalance = outAccount.getAmount();
            e.printStackTrace();
        } finally {
            if (getLock) {
                lock.unlock();
            }
        }
        return outAccountBalance;
    }
}
