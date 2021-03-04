package api;

import java.math.BigDecimal;

public interface TransferOut {
    BigDecimal recharge(String username, BigDecimal rechargeMoney, String requestId);

    BigDecimal transferOut(String inTransferUser, String outTransferUser, BigDecimal transferMoney, String requestId);
}
