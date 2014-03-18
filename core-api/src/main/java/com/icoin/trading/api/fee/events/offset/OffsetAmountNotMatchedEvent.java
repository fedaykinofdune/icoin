package com.icoin.trading.api.fee.events.offset;

import com.icoin.trading.api.fee.domain.offset.OffsetId;
import com.icoin.trading.api.fee.domain.offset.OffsetReason;
import org.joda.money.BigMoney;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liougehooa
 * Date: 14-3-18
 * Time: AM7:40
 * To change this template use File | Settings | File Templates.
 */
public class OffsetAmountNotMatchedEvent extends AbstractOffsetEvent<OffsetAmountNotMatchedEvent>{

    private final OffsetReason offsetReason;
    private final BigMoney offsetAmount;
    private final BigMoney arapAmount;
    private final BigMoney receivedPaidAmount;
    private final Date offsetDate;

    public OffsetAmountNotMatchedEvent(OffsetId offsetId, OffsetReason offsetReason, BigMoney offsetAmount, BigMoney arapAmount, BigMoney receivedPaidAmount, Date offsetDate) {
        super(offsetId);
        this.offsetReason = offsetReason;
        this.offsetAmount = offsetAmount;
        this.arapAmount = arapAmount;
        this.receivedPaidAmount = receivedPaidAmount;
        this.offsetDate = offsetDate;
    }

    public OffsetReason getOffsetReason() {
        return offsetReason;
    }

    public BigMoney getOffsetAmount() {
        return offsetAmount;
    }

    public BigMoney getArapAmount() {
        return arapAmount;
    }

    public BigMoney getReceivedPaidAmount() {
        return receivedPaidAmount;
    }

    public Date getOffsetDate() {
        return offsetDate;
    }
}
