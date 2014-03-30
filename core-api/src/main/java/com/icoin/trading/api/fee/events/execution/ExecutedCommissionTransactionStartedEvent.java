package com.icoin.trading.api.fee.events.execution;

import com.icoin.trading.api.coin.domain.CoinId;
import com.icoin.trading.api.fee.domain.FeeTransactionId;
import com.icoin.trading.api.fee.domain.fee.FeeId;
import com.icoin.trading.api.fee.domain.offset.OffsetId;
import com.icoin.trading.api.tradeengine.domain.OrderBookId;
import com.icoin.trading.api.tradeengine.domain.PortfolioId;
import com.icoin.trading.api.tradeengine.domain.TradeType;
import com.icoin.trading.api.tradeengine.domain.TransactionId;
import org.joda.money.BigMoney;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liougehooa
 * Date: 14-3-18
 * Time: PM9:35
 * To change this template use File | Settings | File Templates.
 */
public class ExecutedCommissionTransactionStartedEvent<T extends ExecutedCommissionTransactionStartedEvent> extends ExecutedTransactionStartedEvent<T> {
    protected final FeeId paidFeeId;
    protected final FeeId accountPayableFeeId;
    protected final BigMoney commissionAmount;

    public ExecutedCommissionTransactionStartedEvent(FeeTransactionId feeTransactionId,
                                                     FeeId paidFeeId,
                                                     FeeId accountPayableFeeId,
                                                     OffsetId offsetId,
                                                     BigMoney commissionAmount,
                                                     String orderId,
                                                     TransactionId orderTransactionId,
                                                     PortfolioId portfolioId,
                                                     Date tradeTime,
                                                     Date dueDate,
                                                     TradeType tradeType,
                                                     BigMoney tradedPrice,
                                                     BigMoney tradeAmount,
                                                     BigMoney executedMoney,
                                                     OrderBookId orderBookId,
                                                     CoinId coinId) {
        super(feeTransactionId,
                offsetId,
                orderId,
                orderTransactionId,
                portfolioId,
                tradeTime,
                dueDate,
                tradeType,
                tradedPrice,
                tradeAmount,
                executedMoney,
                orderBookId,
                coinId);

        this.paidFeeId = paidFeeId;
        this.accountPayableFeeId = accountPayableFeeId;
        this.commissionAmount = commissionAmount;
    }

    public FeeId getPaidFeeId() {
        return paidFeeId;
    }

    public FeeId getAccountPayableFeeId() {
        return accountPayableFeeId;
    }

    public BigMoney getCommissionAmount() {
        return commissionAmount;
    }
}