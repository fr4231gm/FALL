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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div class="banner">
	<a href="#"><img src="${banner}" alt="${systemName}" height=200px width=30% /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<!-- ******************** ADMINISTRATOR ******************** -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/register.do"><spring:message code="master.page.administrator.create.new.administrator" /></a></li>
					<li><a href="dashboard/administrator/list.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.administrator.configuration" /></a></li>
						</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** HACKER ******************** -->
		<security:authorize access="hasRole('HACKER')">
			<li><a class="fNiv"><spring:message	code="master.page.hacker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/hacker/list.do"><spring:message code="master.page.hacker.applications" /></a></li>
					<li><a href="finder/hacker/edit.do"><spring:message code="master.page.hacker.finder" /></a></li>
					<li><a href="curricula/hacker/list.do"><spring:message code="master.page.hacker.curricula" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** COMPANY ******************** -->
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/company/list.do"><spring:message code="master.page.company.applications" /></a></li>
					<li><a href="problem/list.do"><spring:message code="master.page.company.problems" /></a></li>
					<li><a href="position/company/list.do"><spring:message code="master.page.company.positions" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** NOT AUTHENTICATED ******************** -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="about-us/index.do"><spring:message code="master.page.about-us" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li><a href="company/register.do"><spring:message code="master.page.register.as.company" /></a></li>
					<li><a href="hacker/register.do"><spring:message code="master.page.register.as.hacker" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<!-- ************************** ALL ************************ -->
		<security:authorize access="permitAll()">
		<li><a class="fNiv" href="company/list.do"><spring:message code="master.page.companies" /></a></li>
		</security:authorize>
		
		<security:authorize access="permitAll()">
		<li><a class="fNiv" href="position/list.do"><spring:message code="master.page.positions" /></a></li>
		</security:authorize>		
		
		<!-- ******************** AUTHENTICATED ******************** -->
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv">
					<spring:message code="master.page.profile" />
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('COMPANY')">
						<li><a href="company/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('HACKER')">
						<li><a href="hacker/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a href="administrator/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
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
