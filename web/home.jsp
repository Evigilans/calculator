<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resource.content" var="rb"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Calculator</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen">
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
</head>

<body>
<main>
    <div class="form">
        <h1>Welcome, User!</h1>
        <ul class="tab-group">
            <li class="tab active"><a href="#calculator">Calculator</a></li>
            <li class="tab"><a href="#credit">Credit</a></li>
        </ul>
        <div class="tab-content">
            <div id="calculator">
                <form action="${pageContext.request.contextPath}/Controller?mode=calculator" method="post">
                    <div class="top-row">
                        <div class="field-wrap">
                            <label>
                                Enter math expression<span class="req">*</span>
                            </label>
                            <input type="text" name="expression" required autocomplete="off"/>
                        </div>
                        <select name="rounding">
                            <option value="HALF_UP">Math rounding</option>
                            <option value="HALF_EVEN">Bank rounding</option>
                            <option value="DOWN">Truncation</option>
                        </select>
                    </div>
                    <button type="submit" class="button button-block"/>
                    Calculate!</button>
                </form>

                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <h1>${errorMessage}</h1>
                    </c:when>
                    <c:when test="${empty result}">
                        <h1>Result: </h1>
                    </c:when>
                    <c:otherwise>
                        <h1>Result: ${result}</h1>
                    </c:otherwise>
                </c:choose>
            </div>

            <div id="credit">
                <form action="${pageContext.request.contextPath}/Controller?mode=credit" method="post">
                    <div class="top-row">
                        <div class="field-wrap">
                            <input type="text" id="minMaxExample" name="date" value="Date (DD.MM.YYYY)"
                                   onfocus="this.select();_Calendar.lcs(this)"
                                   onclick="event.cancelBubble=true;this.select();_Calendar.lcs(this)"
                                   data-yearfrom="-25"
                                   data-yearto="2">
                        </div>
                        <div class="field-wrap">
                            <label>
                                Percent<span class="req">*</span>
                            </label>
                            <input type="text" name="percent" required autocomplete="off"/>
                        </div>
                        <div class="field-wrap">
                            <label>
                                Start sum<span class="req">*</span>
                            </label>
                            <input type="text" name="start" required autocomplete="off"/>
                        </div>
                        <div class="field-wrap">
                            <label>
                                Month payment<span class="req">*</span>
                            </label>
                            <input type="text" name="payment" required autocomplete="off"/>
                        </div>
                    </div>
                    <button type="submit" class="button button-block"/>
                    Count!</button>
                </form>
                <script src="${pageContext.request.contextPath}/js/calendar.js"></script>


                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <h1>${errorMessage}</h1>
                    </c:when>
                    <c:when test="${empty periods}">
                        <h1>Result: </h1>
                    </c:when>
                    <c:otherwise>
                        <h1>Table: </h1>
                        <div class="wrapper">
                            <table class="tbl sortable">
                                <thead>
                                <tr>
                                    <th>Start of period</th>
                                    <th>End of period</th>
                                    <th>Start sum</th>
                                    <th>Sum with percent</th>
                                    <th>Sum after month payment</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="period" items="${periods}">
                                    <tr>
                                        <td><tags:localDate date="${period.startOfPeriod}" pattern="dd.MM.yyyy"/></td>
                                        <td><tags:localDate date="${period.endOfPeriod}" pattern="dd.MM.yyyy"/></td>
                                        <td><fmt:formatNumber value="${period.startSum}" minFractionDigits="2"/></td>
                                        <td><fmt:formatNumber value="${period.sumWithPercent}" minFractionDigits="2"/></td>
                                        <td><fmt:formatNumber value="${period.sumAfterMonthPayment}" minFractionDigits="2"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <script src="${pageContext.request.contextPath}/js/table.js"></script>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/input.js"></script>
</main>
</body>
</html>