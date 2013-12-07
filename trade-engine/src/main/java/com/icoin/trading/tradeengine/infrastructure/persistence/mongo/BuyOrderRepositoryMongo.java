package com.icoin.trading.tradeengine.infrastructure.persistence.mongo;

import com.icoin.trading.tradeengine.domain.model.order.BuyOrder;
import com.icoin.trading.tradeengine.domain.model.order.BuyOrderRepository;
import com.icoin.trading.tradeengine.domain.model.order.OrderBookId;
import com.icoin.trading.tradeengine.domain.model.order.SellOrder;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liougehooa
 * Date: 13-12-6
 * Time: PM9:35
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unused")
public interface BuyOrderRepositoryMongo extends BuyOrderRepository,
        BuyOrderRepositoryMongoCustom,
        CrudRepository<BuyOrder, String> {

//    @Query(value = "{ 'orderBookId' : ?2 , " +
//            "'itemPrice' : { '$gte' : ?1}, " +
//            "'placeDate' : { '$lte' : ?0}, " +
//            "'orderStatus' : 'PENDING' , " +
//            "'orderType' : 'BUY' }, " +
//            "Sort: { 'itemPrice' : -1 , 'placeDate' : 1 , 'itemRemaining' : -1}")
    List<BuyOrder> findDescPendingOrdersByPriceTime(Date toTime, BigDecimal price, OrderBookId orderBookId, int size);

    @Query(value = "{ '_id' : ?0, 'orderStatus' : 'PENDING'}, 'orderType' : 'BUY'")
    BuyOrder findPendingOrder(String id);

//    @Query(value = "{ 'orderBookId' : ?0 , " +
//            "'orderStatus' : 'PENDING' , " +
//            "'orderType' : 'BUY' }, " +
//            "Sort: { 'itemPrice' : -1 , 'placeDate' : 1 , 'itemRemaining' : -1}")
    BuyOrder findHighestPricePendingOrder(OrderBookId orderBookId);
}
