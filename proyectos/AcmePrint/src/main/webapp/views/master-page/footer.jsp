<%--
 * footer.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" />

<div class="footer">
	<hr />

	<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" />

		<jstl:out value="${systemName}" />
	</b>
	<div class="footer-menu">
		<acme:link link="legal-terms/index.do" code="master.page.legal-terms" />
	</div>
</div>