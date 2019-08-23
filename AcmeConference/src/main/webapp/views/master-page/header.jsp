<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="banner">
	<a href="#"><img src="${banner}" alt="${systemName}" height=200px
		width=30% /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<!-- ADMINISTRATOR -->

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv parent"><i class="fa fa-user-circle-o"></i>&nbsp;&nbsp;&nbsp;<spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a class="child" href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a class="child" href="message/broadcast-everyone.do"> <spring:message code="master.page.broadcast.everyone" /></a></li>
					<li><a class="child" href="message/broadcast-everyone-and-admins.do"> <spring:message code="master.page.broadcast.everyone.and.admins" /></a></li>
					<li><a class="child" href="configuration/administrator/edit.do"><spring:message code="master.page.administrator.configuration" /></a></li>
					<li><a class="child" href="submission/administrator/list.do"><spring:message code="master.page.author.submission" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<!-- AUTHOR -->

		<security:authorize access="hasRole('AUTHOR')">
			<li><a class="fNiv parent"><i class="fa fa-user-circle-o"></i>&nbsp;&nbsp;&nbsp;<spring:message
						code="master.page.author" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a class="child" href="registration/author/list.do"><spring:message
								code="master.page.author.registration" /></a></li>
					<li><a class="child" href="submission/author/list.do"><spring:message
								code="master.page.author.submission" /></a></li>
					<li><a class="child" href="finder/author/edit.do"><spring:message
								code="master.page.author.finder" /></a></li>

				</ul></li>
		</security:authorize>
		
		<!-- SPONSOR -->
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv parent"><i class="fa fa-user-circle-o"></i>&nbsp;&nbsp;&nbsp;<spring:message
						code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a class="child" href="sponsorship/sponsor/list.do"><spring:message
								code="master.page.sponsor.sponsorships" /></a></li>
					

				</ul></li>
		</security:authorize>


		<!-- ******************** NOT AUTHENTICATED ******************** -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><i
					class="fa fa-sign-in"></i>&nbsp;&nbsp;&nbsp;<spring:message
						code="master.page.login" /></a></li>

			<li><a class="fNiv"><i class="fa fa-user-plus"></i>&nbsp;&nbsp;&nbsp;<spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a class="child" href="author/register.do"><spring:message
								code="master.page.register.author" /></a></li>
					<li>
					<li><a class="child" href="sponsor/register.do"><spring:message
								code="master.page.register.sponsor" /></a></li>
					<li><a class="child" href="reviewer/register.do"><spring:message
								code="master.page.register.reviewer" /></a></li>
				</ul></li>

		</security:authorize>

		<!-- ************************** ALL ************************ -->
		<li><a class="fNiv"><i class="fa fa-comments"></i>&nbsp;&nbsp;&nbsp;<spring:message code="master.page.conferences" /></a>
			<ul>
			<li class="arrow"></li>
			<li>
				<a class="child" href="conference/listForthcomingConferences.do"><spring:message code="master.page.listForthcomingConferences" /></a>
			</li>
			<li>
				<a class="child" href="conference/listRunningConferences.do"><spring:message code="master.page.listRunningConferences" /></a>
			</li>
			<li>
				<a class="child" href="conference/listPastConferences.do"><spring:message code="master.page.listPastConferences" /></a>
			</li>
			<security:authorize access="hasRole('ADMINISTRATOR')">
					<li><a class="child" href="conference/administrator/listbyfivedays.do"><spring:message code="master.page.administrator.listbyfivedays" /></a></li>
					<li><a class="child" href="conference/administrator/listAllConferences.do"><spring:message code="master.page.administrator.all" /></a></li>
			</security:authorize>
			
			<li><a class="child" href="category/list.do"><spring:message code="master.page.categories" /></a></li>	
			</ul> 	</li>
		<li><a class="fNiv" href="about-us/index.do"><i class="fa fa-id-card"></i>&nbsp;&nbsp;&nbsp;<spring:message code="master.page.about-us" /></a></li>
			
		<!-- ******************** AUTHENTICATED ******************** -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><i class="fa fa-user"></i>&nbsp;&nbsp;&nbsp;<spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />) </a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/list.do"><spring:message
								code="master.page.messages" /></a></li>
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a href="administrator/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('AUTHOR')">
						<li><a href="author/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('REVIEWER')">
						<li><a href="reviewer/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="sponsor/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
