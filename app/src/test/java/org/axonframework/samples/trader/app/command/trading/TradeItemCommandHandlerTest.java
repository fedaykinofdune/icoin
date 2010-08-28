package org.axonframework.samples.trader.app.command.trading;

import org.axonframework.samples.trader.app.api.tradeitem.CreateTradeItemCommand;
import org.axonframework.samples.trader.app.api.tradeitem.TradeItemCreatedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Jettro Coenradie
 */
public class TradeItemCommandHandlerTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = Fixtures.newGivenWhenThenFixture();
        TradeItemCommandHandler commandHandler = new TradeItemCommandHandler();
        commandHandler.setRepository(fixture.createGenericRepository(TradeItem.class));
        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void testCreateTradeItem() {
        UUID userId = UUID.randomUUID();
        CreateTradeItemCommand command = new CreateTradeItemCommand(userId, "TestItem", 1000, 10000);

        fixture.given()
                .when(command)
                .expectEvents(new TradeItemCreatedEvent("TestItem", 1000, 10000));
    }
}
