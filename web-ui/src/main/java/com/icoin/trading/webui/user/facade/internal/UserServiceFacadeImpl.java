package com.icoin.trading.webui.user.facade.internal;

import com.homhon.base.domain.service.UserService;
import com.homhon.util.Asserts;
import com.homhon.util.Strings;
import com.icoin.trading.tradeengine.query.portfolio.PortfolioEntry;
import com.icoin.trading.tradeengine.query.portfolio.repositories.PortfolioQueryRepository;
import com.icoin.trading.users.application.command.ChangePasswordCommand;
import com.icoin.trading.users.application.command.ChangeWithdrawPasswordCommand;
import com.icoin.trading.users.application.command.ForgetPasswordCommand;
import com.icoin.trading.users.application.command.ResetPasswordCommand;
import com.icoin.trading.users.domain.ForgetPasswordEmailSender;
import com.icoin.trading.users.domain.model.function.TooManyResetsException;
import com.icoin.trading.users.domain.model.function.UserPasswordReset;
import com.icoin.trading.users.domain.model.function.UserPasswordResetRepository;
import com.icoin.trading.users.domain.model.user.UserAccount;
import com.icoin.trading.users.domain.model.user.UserId;
import com.icoin.trading.users.query.UserEntry;
import com.icoin.trading.users.query.repositories.UserQueryRepository;
import com.icoin.trading.webui.user.facade.UserServiceFacade;
import org.apache.commons.lang3.time.DateUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.homhon.util.Collections.isEmpty;
import static com.homhon.util.Asserts.notNull;
import static com.homhon.util.Asserts.hasLength;

/**
 * Created with IntelliJ IDEA.
 * User: jihual
 * Date: 12/17/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserServiceFacadeImpl implements UserServiceFacade {
    private static Logger logger = LoggerFactory.getLogger(UserServiceFacadeImpl.class);

    private UserService userService;
    private UserQueryRepository userRepository;
    private PortfolioQueryRepository portfolioQueryRepository;

    private CommandGateway commandGateway;

    private ForgetPasswordEmailSender emailSender;

    private UserQueryRepository userQueryRepository;

    private UserPasswordResetRepository userPasswordResetRepository;

    @Override
    public UserAccount currentUser() {
        return (UserAccount) userService.getCurrentUser();
    }

    /**
     * For now we work with only one portfolio per user. This might change in the future.
     *
     * @return The found portfolio for the logged in user.
     */
    public PortfolioEntry obtainPortfolioForUser() {
        UserAccount userAccount = currentUser();

        if (userAccount == null) {
            logger.warn("user not logged on");
            return null;
        }

        UserEntry username = userRepository.findByUsername(userAccount.getPrimaryKey());
        return portfolioQueryRepository.findByUserIdentifier(username.getPrimaryKey());
    }

    @Override
    public void changePassword(String previousPassword, String newPassword, String confirmedNewPassword, String operatingIp) {
        UserAccount userAccount = currentUser();
        if (userAccount == null) {
            logger.warn("user not logged on");
            return;
        }
        commandGateway.send(new ChangePasswordCommand(new UserId(userAccount.getPrimaryKey()),
                userAccount.getUserName(), previousPassword, newPassword, confirmedNewPassword,
                operatingIp));
    }

    public boolean generateForgetPasswordToken(String email, String operatingIp, Date currentTime) {
        UserAccount userAccount = currentUser();
        if (userAccount != null) {
            logger.warn("user already logged on");
            return false;
        }

        ForgetPasswordCommand command = new ForgetPasswordCommand(email, operatingIp, currentTime);

        String token = commandGateway.sendAndWait(command, 10, TimeUnit.SECONDS);

        if (!Strings.hasLength(token)) {
            return false;
        }

        emailSender.sendEmail(userAccount, token, email);

        return true;
    }


    @Override
    public void resetPasswordWithToken(String token, String password, String confirmedPassword, String operatingIp) {
        UserAccount userAccount = currentUser();
        if (userAccount == null) {
            logger.warn("user not logged on");
            return;
        }
        ResetPasswordCommand command = new ResetPasswordCommand(token, password, confirmedPassword, operatingIp);

        commandGateway.send(command);
    }



    @Override
    public void changeWithdrawPassword(String previousPassword, String withdrawPassword, String confirmedWithdrawPassword, String operatingIp) {
        UserAccount userAccount = currentUser();
        if (userAccount == null) {
            logger.warn("user not logged on");
            return;
        }
        commandGateway.send(new ChangeWithdrawPasswordCommand(new UserId(userAccount.getPrimaryKey()),
                userAccount.getUserName(), previousPassword, withdrawPassword, confirmedWithdrawPassword, operatingIp));
    }

    @Override
    public UserEntry findByEmail(String email) {
        if(!Strings.hasText(email)){
            return null;
        }
        UserEntry user = userQueryRepository.findByEmail(email);

        if (user == null) {
            logger.warn("can not find user by email!", email);
            return null;
        }

        return user;
    }

    @Override
    public int findPasswordResetCount(String username, String ip, Date currentDate) {
        hasLength(username);
        hasLength(ip);
        notNull(currentDate);

        Date startDate = DateUtils.addDays(currentDate, -1);

        List<UserPasswordReset> resets = userPasswordResetRepository.findNotExpiredByUsername(username, ip, startDate, currentDate);

        if (isEmpty(resets)) {
            return 0;
        }
        return resets.size();
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setUserRepository(UserQueryRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setPortfolioQueryRepository(PortfolioQueryRepository portfolioQueryRepository) {
        this.portfolioQueryRepository = portfolioQueryRepository;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setUserQueryRepository(UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setUserPasswordResetRepository(UserPasswordResetRepository userPasswordResetRepository) {
        this.userPasswordResetRepository = userPasswordResetRepository;
    }

    @Autowired
    public void setEmailSender(ForgetPasswordEmailSender emailSender) {
        this.emailSender = emailSender;
    }
}