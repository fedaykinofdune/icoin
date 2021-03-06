package com.icoin.trading.api.tradeengine.command.admin;

import com.homhon.base.command.CommandSupport;
import com.icoin.trading.api.tradeengine.domain.ChangedReason;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jihual
 * Date: 3/5/14
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisabledTradingCommand extends CommandSupport<DisabledTradingCommand> {
    @NotNull(message = "start trading time cannot be null")
    private Date allowedToTradeStartTime;
    @NotEmpty(message = "please provide changed by")
    private String changedBy;
    @NotNull(message = "please provide changed time")
    private Date changedTime;
    @NotNull(message = "please provide change reason")
    private ChangedReason reason;


    public DisabledTradingCommand(Date allowedToTradeStartTime, String changedBy, Date changedTime, ChangedReason reason) {
        this.allowedToTradeStartTime = allowedToTradeStartTime;
        this.changedBy = changedBy;
        this.changedTime = changedTime;
        this.reason = reason;
    }

    public Date getAllowedToTradeStartTime() {
        return allowedToTradeStartTime;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public Date getChangedTime() {
        return changedTime;
    }

    public ChangedReason getReason() {
        return reason;
    }
}