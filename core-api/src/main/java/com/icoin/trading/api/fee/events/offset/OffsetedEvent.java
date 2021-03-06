package com.icoin.trading.api.fee.events.offset;

import com.icoin.trading.api.fee.domain.offset.OffsetId;
import org.joda.money.BigMoney;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liougehooa
 * Date: 14-3-18
 * Time: AM7:40
 * To change this template use File | Settings | File Templates.
 */
public class OffsetedEvent extends AbstractOffsetEvent<OffsetedEvent> {

    private final BigMoney offsetAmount;
    private final Date offsetDate;

    public OffsetedEvent(OffsetId offsetId, BigMoney offsetAmount, Date offsetDate) {
        super(offsetId);
        this.offsetAmount = offsetAmount;
        this.offsetDate = offsetDate;
    }

    public BigMoney getOffsetAmount() {
        return offsetAmount;
    }

    public Date getOffsetDate() {
        return offsetDate;
    }
}
