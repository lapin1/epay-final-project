<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>My Account</title>
    <link href="./css/header.css" rel="stylesheet" type="text/css">
    <link href="./css/accountPageStyle.css" rel="stylesheet" type="text/css">
    <link href="./css/details.css" rel="stylesheet" type="text/css">

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>

    <fmt:message bundle="${loc}" key="locale.en.button" var="en_btn"/>
    <fmt:message bundle="${loc}" key="locale.ru.button" var="ru_btn"/>
    <fmt:message bundle="${loc}" key="locale.account.label" var="account_label"/>
    <fmt:message bundle="${loc}" key="locale.balance" var="balance"/>
    <fmt:message bundle="${loc}" key="locale.status" var="status"/>
    <fmt:message bundle="${loc}" key="locale.back.to.accounts.button" var="back"/>
    <fmt:message bundle="${loc}" key="locale.payment.label" var="payment_label"/>
    <fmt:message bundle="${loc}" key="locale.card.label" var="card_label"/>
    <fmt:message bundle="${loc}" key="locale.transaction.label" var="transaction_label"/>
    <fmt:message bundle="${loc}" key="locale.block.account.button" var="block"/>
    <fmt:message bundle="${loc}" key="locale.top.up.account.button" var="topup"/>
    <fmt:message bundle="${loc}" key="locale.card" var="card_name"/>
    <fmt:message bundle="${loc}" key="locale.sum" var="sum"/>
    <fmt:message bundle="${loc}" key="locale.transfer.to.account.label" var="acc_number"/>
    <fmt:message bundle="${loc}" key="locale.pay.with.epay" var="pay_with_epay"/>
    <fmt:message bundle="${loc}" key="locale.pay.with.card" var="pay_with_card"/>
    <fmt:message bundle="${loc}" key="locale.pay.with.button.part" var="pay_with_part"/>
    <fmt:message bundle="${loc}" key="locale.epay.label.button.part" var="epay_part"/>
    <fmt:message bundle="${loc}" key="locale.select.a.card.label" var="select_card"/>
    <fmt:message bundle="${loc}" key="locale.delete.button" var="remove_card"/>
    <fmt:message bundle="${loc}" key="locale.add.card.button" var="add_card"/>
    <fmt:message bundle="${loc}" key="locale.card.number.label" var="card_number_label"/>
    <fmt:message bundle="${loc}" key="locale.exp.date" var="exp_date"/>
    <fmt:message bundle="${loc}" key="locale.recipient.label" var="recipient_label"/>
    <fmt:message bundle="${loc}" key="locale.recipient.status" var="recipient_status"/>

</head>
<body>

<header>
    <div id="left">
        ePay
    </div>
    <div id="right">
        <ul>
            <li><a href="#">
                <div class="burger"></div>
                <div class="burger"></div>
                <div class="burger"></div>
            </a>
                <ul>
                    <li><a id="1" href="/about">${userName}</a></li>
                    <li><a href="MyController?command=logout">logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
    </div>
</header>


<h2><a href="MyController?command=GO_TO_HOME_PAGE">${back}</a></h2>


<div class="tabs">
    <input type="radio" name="tab-btn" id="tab-btn-1" value="" checked>
    <label for="tab-btn-1">${account_label}</label>
    <input type="radio" name="tab-btn" id="tab-btn-2" value="">
    <label for="tab-btn-2">${payment_label}</label>
    <input type="radio" name="tab-btn" id="tab-btn-3" value="">
    <label for="tab-btn-3">${card_label}</label>
    <input type="radio" name="tab-btn" id="tab-btn-4" value="">
    <label for="tab-btn-4">${transaction_label}</label>


    <div id="content-1">
        <p>${account_label} № ${selectedAccount.accountNumber}</p>
        <p>${balance}: ${selectedAccount.balance}</p>
        <p>${status}: ${selectedAccount.status}</p>


        <form action="MyController" method="post">
            <input type="hidden" name="command" value="blockAccount">
            <input ${selectedAccount.status eq "BLOCKED" ? 'disabled = "disabled"' : ''} type="submit"
                                                                                         value="${block}"/>
        </form>


        <label class="btn" for="modal-1">${topup}</label>
        <div>
            <h4 style="color: greenyellow">${param.message}</h4>
            <h4 style="color: red">${param.error}</h4>
            <h4 style="color: red">${errorList.get("card number error")}</h4>
            <h4 style="color: red">${errorList.get("expiration date error")}</h4>
            <h4 style="color: red">${errorList.get("cvv error")}</h4>
            <h4 style="color: red">${errorList}</h4>
        </div>

        <div class="modal">
            <input ${selectedAccount.status eq "BLOCKED" ? 'disabled = "disabled"' : ''} class="modal-open" id="modal-1"
                                                                                         type="checkbox" hidden>
            <div class="modal-wrap" aria-hidden="true" role="dialog">
                <label class="modal-overlay" for="modal-1"></label>
                <div class="modal-dialog">
                    <div class="modal-header">
                        <h2>${topup} </h2>
                        <label class="btn-close" for="modal-1" aria-hidden="true">×</label>
                    </div>
                    <div class="modal-body">
                        <form action="MyController" method="post">
                            <input ${selectedAccount.status eq "BLOCKED" ? 'disabled = "disabled"' : ''} type="hidden"
                                                                                                         name="command"
                                                                                                         value="topUpAccount">
                            <select name="selectedCard">
                                <c:forEach items="${cards}" var="card">
                                    <option value="${card.cardNumber}">${card_name}: ${card.cardNumber} ${balance}: ${card.balance} </option>
                                </c:forEach>
                            </select>
                            ${sum}:
                            <input type="text" size="8" name="sum" value=""/>
                            <br>
                            <div class="modal-footer">
                                <input ${selectedAccount.status eq "BLOCKED" ? 'disabled = "disabled"' : ''}
                                        class="btn btn-primary" for="modal-1" type="submit" value="${topup}"/>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>


    <div id="content-2" >

        <div class="tab2">
            <form action="MyController" method="post">
                <div class="recipient">
                    <div>
                        <label>${acc_number}:</label>
                        <input type="text" name="accountNumberTo" value=""/>
                    </div>
                    <br>
                    <div>
                        <label>${sum}:</label>
                        <input type="text" name="sum" value=""/>
                    </div>
                </div>

                <div class="acc-pay">
                    <details>
                        <summary>${pay_with_epay}</summary>
                        <div>
                            <button class="epay-btn" type="submit" name="command"
                                    value="transferToAccount">${pay_with_part} <b>${epay_part}</b></button>
                        </div>
                    </details>
                </div>
                or<br><br>
                <div class="card-pay">
                    <details>
                        <summary>${pay_with_card}</summary>
                        <div>
                            ${select_card}:&nbsp;
                            <br><br>
                            <select name="selectedCard">
                                <c:forEach items="${cards}" var="card">
                                    <option value="${card.cardNumber}">card: ${card.cardNumber}
                                        balance: ${card.balance}</option>
                                </c:forEach>
                            </select>
                            <br><br>
                            <button class="card-btn" type="submit" name="command"
                                    value="transferByCardToAccount">${pay_with_card}</button>
                        </div>
                    </details>
                </div>
            </form>
        </div>
    </div>

    <div id="content-3">

        <c:forEach var="card" varStatus="countOfCards" items="${cards}">
            <div class="card-div">
                <tr>
                    <td>id <c:out value="${card.id}"/></td>

                    <td><c:out value="${card.cardNumber}"/></td>

                    <td>${balance} <c:out value="${card.balance}"/></td>

                </tr>
                <a style="border: #9a5757" href="MyController?command=deleteCard&id=<c:out value="${card.id}"/>">${remove_card}</a>
            </div>

        </c:forEach>


        <label ${countOfCards == 4 ? 'disabled' : ''} class="btn" for="modal-3">${add_card}</label>

        <div class="modal">
            <input class="modal-open" id="modal-3" type="checkbox" hidden>
            <div class="modal-wrap" aria-hidden="true" role="dialog">
                <label class="modal-overlay" for="modal-3"></label>
                <div class="modal-dialog">
                    <div class="modal-header">
                        <h2>add a card </h2>
                        <label class="btn-close" for="modal-3" aria-hidden="true">×</label>
                    </div>
                    <div class="modal-body">
                        <form action="MyController" method="post">
                            <input type="hidden" name="command" value="addCard">
                            ${card_number_label}:
                            <input type="text" name="cardNumber" value=""/>
                            <br>
                            ${exp_date}
                            <input type="text" name="month" value=""/> / <input type="text" name="year" value=""/>
                            <br>
                            cvv:
                            <input type="text" name="cvv" value=""/>
                            ${errorList}

                            <br>
                            <div class="modal-footer">
                                <input class="btn btn-primary" for="modal-3" type="submit" value="${add_card}"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <br>
    </div>

    <div id="content-4">

        <c:forEach var="acctr" items="${acctr}">
            <div>
                <tr>
                    <td><c:out value="${acctr.date}"/></td>
                    <td>${recipient_label} <c:out value="${acctr.recipient}"/></td>
                    <td>${sum} <c:out value="${acctr.sum}"/></td>
                    <td>${recipient_status} <c:out value="${acctr.status}"/></td>
                </tr>
            </div>
        </c:forEach>


       


    </div>
</div>
</div>


<div>
    <p>
    <form action="MyController" method="get">
        <input type="hidden" name="command" value="changeLanguage"/>
        <input type="hidden" name="locale" value="ru"/>
        <input type="submit" value="${ru_btn}"/>
    </form>
    <form action="MyController" method="get">
        <input type="hidden" name="command" value="changeLanguage"/>
        <input type="hidden" name="locale" value="en"/>
        <input type="submit" value="${en_btn}"/>
    </form>
    </p>
</div>

</body>
</html>
