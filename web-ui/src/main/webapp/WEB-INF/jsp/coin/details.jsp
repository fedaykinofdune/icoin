<%@include file="../include.jsp" %>
<%--
  ~ Copyright (c) 2010-2012. Axon Framework
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<html>
<head>
    <title>Coin details</title>
</head>
<body>
<content tag="title"><c:out value="${coin.name}"/></content>
<content tag="tagline">
    <span class="detailLabel">Value : </span>
    <span><c:out value="${coin.coinPrice}"/></span>
    <span class="detailLabel"># Shares : </span>
    <span><c:out value="${coin.coinAmount}"/></span>
</content>
<content tag="breadcrumb">
    <ol class="breadcrumb">
        <li><a href="${ctx}/">Home</a></li>
        <li><a href="${ctx}/coin">Coins</a></li>
        <li class="active"><c:out value='${coin.name}'/></li>
    </ol>
</content>


<c:if test="${coin.tradeStarted}">
    <div class="row">
        <div class="span14">
            <p>
                <a class="btn primary"
                   href="${ctx}/coin/buy/<c:out value='${coin.primaryKey}'/>">Buy &raquo;</a>
                <a class="btn primary"
                   href="${ctx}/coin/sell/<c:out value='${coin.primaryKey}'/>">Sell &raquo;</a>
            </p>
        </div>
    </div>
</c:if>
<div class="row">
    <div class="span5">
        <h3>Sell Orders</h3>
        <table class="zebra-striped">
            <thead>
            <tr>
                <th>Count</th>
                <th>Price</th>
                <th>Remaining</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sellOrders}" var="order">
                <tr>
                    <td><c:out value='${order.tradeAmount}'/></td>
                    <td><c:out value='${order.itemPrice}'/></td>
                    <td><c:out value='${order.itemRemaining}'/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="span5">
        <h3>Buy Orders</h3>
        <table class="zebra-striped">
            <thead>
            <tr>
                <th>Count</th>
                <th>Price</th>
                <th>Remaining</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${buyOrders}" var="order">
                <tr>
                    <td><c:out value='${order.tradeAmount}'/></td>
                    <td><c:out value='${order.itemPrice}'/></td>
                    <td><c:out value='${order.itemRemaining}'/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="span4">
        <h3>Executed trades</h3>
        <table class="zebra-striped">
            <thead>
            <tr>
                <th>Count</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${executedTrades}" var="trade">
                <tr>
                    <td><c:out value='${trade.tradedAmount}'/></td>
                    <td><c:out value='${trade.tradedPrice}'/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>