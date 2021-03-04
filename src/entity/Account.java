package entity;

import utils.LogUtil;

import java.math.BigDecimal;

/**
 * @author zhoutzzz
 */
public class Account {
    private final String idCard;

    private final String username;

    private BigDecimal amount;


    public Account(String idCard) {
        if (idCard.isBlank()) {
            LogUtil.log("idCard must be not null");
        }
        this.idCard = idCard;
        this.username = idCard.substring(idCard.length() - 12);
        this.amount = BigDecimal.ZERO;
    }

    public BigDecimal transferAmount(String username, BigDecimal transferOut) {
        if (!this.username.equalsIgnoreCase(username)) {
            LogUtil.log("this account's username error, transfer failed");
        }

        BigDecimal curMoney = this.amount.subtract(transferOut);
        if (curMoney.compareTo(BigDecimal.ZERO) >= 0) {
            this.amount = curMoney;
        } else {
            LogUtil.log("this account don't have enough money to transfer out, idCard: " + this.idCard);
        }
        return this.amount;
    }

    public BigDecimal recharge(String username, BigDecimal money) {
        if (this.username.equalsIgnoreCase(username)) {
            this.amount = this.amount.add(money);
        } else {
            LogUtil.log("this account's username error, recharge failed");
        }
        return this.amount;
    }

    public String getIdCard() {
        return this.idCard;
    }

    public String getUsername() {
        return this.username;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
}
