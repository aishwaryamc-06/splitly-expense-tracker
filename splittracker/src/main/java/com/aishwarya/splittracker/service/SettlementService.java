
package com.aishwarya.splittracker.service;

import com.aishwarya.splittracker.MemberBalance;
import com.aishwarya.splittracker.Settlement;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SettlementService {

    private final MemberBalanceService balanceService;

    public SettlementService(MemberBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public List<Settlement> calculateSettlements(Long groupId) {

        List<MemberBalance> balances =
                balanceService.getGroupBalances(groupId);

        List<MemberBalance> creditors = new ArrayList<>();
        List<MemberBalance> debtors = new ArrayList<>();

        for (MemberBalance balance : balances) {

            if (balance.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(balance);
            } else if (balance.getBalance()
                    .compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(balance);
            }
        }

        creditors.sort(
                Comparator.comparing(MemberBalance::getBalance).reversed()
        );

        debtors.sort(
                Comparator.comparing(MemberBalance::getBalance)
        );

        List<Settlement> settlements = new ArrayList<>();

        int creditorIndex = 0;
        int debtorIndex = 0;

        while (creditorIndex < creditors.size()
                && debtorIndex < debtors.size()) {

            MemberBalance creditor = creditors.get(creditorIndex);
            MemberBalance debtor = debtors.get(debtorIndex);

            BigDecimal amountOwed =
                    debtor.getBalance().abs();

            BigDecimal amountReceivable =
                    creditor.getBalance();

            BigDecimal payment = amountOwed.min(amountReceivable);

            settlements.add(new Settlement(
                    debtor.getMemberName(),
                    creditor.getMemberName(),
                    payment
            ));

            debtor.setBalance(
                    debtor.getBalance().add(payment)
            );

            creditor.setBalance(
                    creditor.getBalance().subtract(payment)
            );

            if (debtor.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                debtorIndex++;
            }

            if (creditor.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                creditorIndex++;
            }
        }

        return settlements;
    }
}
