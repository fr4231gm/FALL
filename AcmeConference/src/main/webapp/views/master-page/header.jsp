<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div class="banner">
	<a href="#"><img src="${banner}" alt="${systemName}" height=200px width=30% /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		
		<!-- ******************** NOT AUTHENTICATED ******************** -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="about-us/index.do"><spring:message code="master.page.about-us" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
			</li>
		</security:authorize>
		
		<!-- ************************** ALL ************************ -->
		
		
		<!-- ******************** AUTHENTICATED ******************** -->
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv">
					<spring:message code="master.page.profile" />
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="about-us/index.do"><spring:message code="master.page.about-us" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
