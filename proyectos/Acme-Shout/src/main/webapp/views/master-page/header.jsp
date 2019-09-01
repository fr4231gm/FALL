<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

		<script type="text/javascript">
			var clock;
			
			$(document).ready(function() {
				clock = $('.clock').FlipClock({
					clockFace: 'TwentyFourHourClock'
				});
			});
		</script>
	
		<div class="clock"  style=" margin-left: 12em;"></div>
		
		<a href="#"><img src="images/logo.png" alt="Acme shout Co., Inc." /></a>
		
			
<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/statistics.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/bar.do"><spring:message code="master.page.administrator.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/shouts.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/shout.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/inspiringQuotes.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/calculator.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/panic.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	 <a href="?language=fr"><img class="idioma" src="images/fr.png" alt="fr"/></a><a href="?language=en"><img class="idioma" src="images/en.png" alt="en" /></a><a href="?language=es"><img class="idioma" src="images/es.png"alt="es"/></a>
</div>

