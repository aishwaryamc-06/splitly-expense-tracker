
package com.aishwarya.splittracker;

import java.math.BigDecimal;

public class MemberBalance {

    private Long memberId;
    private String memberName;
    private BigDecimal balance;

    public MemberBalance() {
    }

    public MemberBalance(
            Long memberId,
            String memberName,
            BigDecimal balance) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.balance = balance;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
