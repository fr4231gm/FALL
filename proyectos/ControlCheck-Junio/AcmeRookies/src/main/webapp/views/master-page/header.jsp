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
		
		<!-- ******************** ADMINISTRATOR ******************** -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<jstl:if test="${not config.rebrandingNotificated }">
					<li><a href="administrator/notificateRebranding.do"><spring:message code="master.page.notificate.rebranding" /></a></li>
					</jstl:if>
					<li><a href="administrator/register.do"><spring:message code="master.page.administrator.create.new.administrator" /></a></li>
					<li><a href="auditor/register.do"><spring:message code="master.page.administrator.create.new.auditor" /></a></li>
					<li><a href="dashboard/administrator/list.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.administrator.configuration" /></a></li>
					<li><a href="actor/administrator/spammer.do"><spring:message code="master.page.administrator.launch.spammer.process" /></a></li>
					<li><a href="actor/administrator/list.do"><spring:message code="master.page.administrator.actors" /></a></li>
					<li><a href="actor/administrator/score.do"><spring:message code="master.page.administrator.polarity" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** ROOKIE ******************** -->
		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv"><spring:message	code="master.page.rookie" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/rookie/list.do"><spring:message code="master.page.rookie.applications" /></a></li>
					<li><a href="finder/rookie/edit.do"><spring:message code="master.page.rookie.finder" /></a></li>
					<li><a href="curricula/rookie/list.do"><spring:message code="master.page.rookie.curricula" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
				<!-- ******************** AUDITOR ******************** -->
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="audit/auditor/list.do"><spring:message code="master.page.auditor.audits" /></a></li>
					<li><a href="audit/auditor/create.do"><spring:message code="master.page.auditor.audit.create" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** COMPANY ******************** -->
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/company/list.do"><spring:message code="master.page.company.applications" /></a></li>
					<li><a href="problem/company/list.do"><spring:message code="master.page.company.problems" /></a></li>
					<li><a href="position/company/list.do"><spring:message code="master.page.company.positions" /></a></li>
					<li><a href="wolem/company/list.do"><spring:message code="master.page.company.wolems" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** PROVIDER ******************** -->
		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv"><spring:message	code="master.page.provider" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="item/provider/list.do"><spring:message code="master.page.provider.items" /></a></li>
					<li><a href="sponsorship/list.do"><spring:message code="master.page.provider.sponsorships" /></a></li>
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
					<li><a href="rookie/register.do"><spring:message code="master.page.register.as.rookie" /></a></li>
					<li><a href="provider/register.do"><spring:message code="master.page.register.as.provider" /></a></li>
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
		
		<security:authorize access="permitAll()">
		<li><a class="fNiv" href="provider/list.do"><spring:message code="master.page.providers" /></a></li>
		</security:authorize>
			<security:authorize access="permitAll()">
		<li><a class="fNiv" href="item/listAll.do"><spring:message code="master.page.items" /></a></li>
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
					<li><a href="social-profile/list.do"><spring:message code="master.page.profile.my.social.profiles" /></a></li>
					<li><a href="message/list.do"><spring:message code="master.page.messages" /></a></li>
					<security:authorize access="hasAnyRole('PROVIDER, COMPANY, ROOKIE, AUDITOR')">
					<li><a href="actor/erase.do" onclick="return confirm('<spring:message code="actor.confirm.delete" />')" /><spring:message code="actor.erase.want" /></a></li>
					<li><a href="actor/export.do"><spring:message code="master.page.export" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('PROVIDER')">
						<li><a href="provider/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
						<li><a href="company/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ROOKIE')">
						<li><a href="rookie/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a href="message/broadcast.do"><spring:message code="master.page.broadcast" /></a></li>
						<li><a href="administrator/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('AUDITOR')">
						<li><a href="auditor/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="about-us/index.do"><spring:message code="master.page.about-us" /></a></li>
		</security:authorize>
	</ul>
</div>

<div class="idioma">
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
