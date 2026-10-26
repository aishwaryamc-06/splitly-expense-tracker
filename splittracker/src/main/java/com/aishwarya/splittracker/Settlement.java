
package com.aishwarya.splittracker;

import java.math.BigDecimal;

public class Settlement {

    private String fromMemberName;
    private String toMemberName;
    private BigDecimal amount;

    public Settlement() {
    }

    public Settlement(
            String fromMemberName,
            String toMemberName,
            BigDecimal amount) {

        this.fromMemberName = fromMemberName;
        this.toMemberName = toMemberName;
        this.amount = amount;
    }

    public String getFromMemberName() {
        return fromMemberName;
    }

    public String getToMemberName() {
        return toMemberName;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
