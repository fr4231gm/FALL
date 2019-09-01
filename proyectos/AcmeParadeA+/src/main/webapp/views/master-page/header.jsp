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

<div>
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
					<li><a href="position/administrator/list.do"><spring:message code="master.page.administrator.positions" /></a></li>
					<li><a href="area/administrator/list.do"><spring:message code="master.page.administrator.areas" /></a></li>
					<li><a href="message/broadcast.do"><spring:message code="master.page.administrator.broadcast" /></a></li>
					<li><a href="message/notification.do"><spring:message code="master.page.administrator.notification" /></a></li>
					<li><a href="dashboard/administrator/list.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="actor/administrator/choose.do"><spring:message code="master.page.administrator.actors" /></a></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.administrator.system.configuration" /></a></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.administrator.possitive.negative.words" /></a></li>
					<li><a href="actor/administrator/polarity.do"><spring:message code="master.page.administrator.launch.polarity.process" /></a></li>
					<li><a href="actor/administrator/spammers.do"><spring:message code="master.page.administrator.launch.spammer.process" /></a></li>
					<li><a href="sponsorship/administrator/listSponsorships.do"><spring:message code="master.page.administrator.deactive.sponsorships" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<!-- ******************** BROTHERHOOD ******************** -->
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="float/brotherhood/list.do"><spring:message code="master.page.brotherhood.floats" /></a></li>
					<li><a href="parade/brotherhood/list.do"><spring:message code="master.page.brotherhood.parades" /></a></li>
					<li><a href="enrolment/brotherhood/list/assigned.do"><spring:message code="master.page.brotherhood.members" /></a></li>
					<li><a href="request/brotherhood/list.do"><spring:message code="master.page.brotherhood.requests" /></a></li>
					<li><a href="area/brotherhood/list.do"><spring:message code="master.page.brotherhood.areas" /></a></li>
					<li><a href="enrolment/brotherhood/list/unassigned.do"><spring:message code="master.page.brotherhood.pending.enrolments" /></a></li>
					<li><a href="history/brotherhood/display.do"><spring:message code="master.page.brotherhood.my.history" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<!-- ******************** MEMBER ******************** -->
		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message	code="master.page.member" /></a>
				<ul>
					<li class="arrow">
					<li><a href="parade/member/list.do"><spring:message code="master.page.member.new.request" /></a></li>
					<li><a href="request/member/list.do"><spring:message code="master.page.member.my.requests" /></a></li>
					<li><a href="brotherhood/member/list/notenrolled.do"><spring:message code="master.page.member.enrol" /></a></li>
					<li><a href="enrolment/member/list.do"><spring:message code="master.page.member.my.enrolments" /></a></li>
					<li><a href="brotherhood/member/list/enrolled.do"><spring:message code="master.page.member.my.brotherhoods" /></a></li>
					<li><a href="finder/member/edit.do"><spring:message code="master.page.member.finder" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** SPONSOR ******************** -->
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow">
					<li><a href="creditcard/sponsor/list.do"><spring:message code="master.page.sponsor.my.creditcards" /></a></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsor.my.sponsorships" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<!-- ******************** CHAPTER ******************** -->
		<security:authorize access="hasRole('CHAPTER')">
			<li><a class="fNiv"><spring:message	code="master.page.chapter" /></a>
				<ul>
					<li class="arrow">
					<li><a href="chapter/selectArea.do"><spring:message code="master.page.chapter.select.area" /></a></li>
					<li><a href="chapter/manageParades.do"><spring:message code="master.page.chapter.manage.parades" /></a></li>
					<li><a href="proclaim/chapter/list.do"><spring:message code="master.page.chapter.manage.proclaims" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<!-- ******************** NOT AUTHENTICATED ******************** -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li><a href="member/register.do"><spring:message code="master.page.register.as.member" /></a></li>
					<li><a href="brotherhood/register.do"><spring:message code="master.page.register.as.brotherhood" /></a></li>
					<li><a href="sponsor/register.do"><spring:message code="master.page.register.as.sponsor" /></a></li>
					<li><a href="chapter/register.do"><spring:message code="master.page.register.as.chapter" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<!-- ************************** ALL ************************ -->
		<security:authorize access="permitAll()">
		<li><a class="fNiv" href="brotherhood/list.do"><spring:message code="master.page.brotherhoods" /></a></li>
		<li><a class="fNiv" href="chapter/list.do"><spring:message code="master.page.chapters" /></a></li>
			<li><a class="fNiv" href="about-us/index.do"><spring:message code="master.page.about.us" /></a></li>
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
						<security:authorize access="hasRole('MEMBER')">
							<li><a href="member/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
						</security:authorize>
						<security:authorize access="hasRole('ADMINISTRATOR')">
							<li><a href="administrator/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
						</security:authorize>
						<security:authorize access="hasRole('BROTHERHOOD')">
							<li><a href="brotherhood/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
						</security:authorize>
						<security:authorize access="hasRole('SPONSOR')">
							<li><a href="sponsor/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
						</security:authorize>
						<security:authorize access="hasRole('CHAPTER')">
							<li><a href="chapter/edit.do"><spring:message code="master.page.profile.edit.my.profile" /></a></li>
						</security:authorize>
					<li><a href="box/list.do"><spring:message code="master.page.profile.my.boxes" /></a></li>
					<li><a href="message/create.do"><spring:message code="master.page.profile.send.message" /></a></li>
					<li><a href="social-profile/list.do"><spring:message code="master.page.profile.my.social.profiles" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
