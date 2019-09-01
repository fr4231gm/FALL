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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<div class="block-language">
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

<div class="banner">
	<a href="#"><img src="${banner}" alt="${systemName}" height=200px
		width=30% /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<!-- ******************** ADMINISTRATOR ******************** -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv parent"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li><a class="child" href="administrator/register.do"><spring:message
								code="master.page.administrator.create.new.administrator" /></a></li>
					<li><a class="child" href="dashboard/administrator/list.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
					<li><a class="child" href="configuration/administrator/edit.do"><spring:message
								code="master.page.administrator.configuration" /></a></li>

					<li><a class="child" href="actor/administrator/list.do"><spring:message
								code="master.page.administrator.actors" /></a></li>
					<li><a class="child" href="actor/administrator/spammer.do"><spring:message
								code="master.page.administrator.launch.spammer.process" /></a></li>

					<li><a class="child" href="actor/administrator/score.do"><spring:message
								code="master.page.administrator.score" /></a></li>

				</ul></li>
		</security:authorize>


		<!-- ******************** CUSTOMER ******************** -->
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv parent"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a class="child" href="order/customer/list.do"><spring:message
								code="master.page.customer.orders" /></a></li>
					<li><a class="child" href="invoice/customer/list.do"><spring:message
								code="master.page.customer.invoices" /></a></li>
					<li><a class="child" href="creditcard/display.do"><spring:message
								code="master.page.provider.creditcard" /></a></li>
				</ul></li>
		</security:authorize>

		<!-- ******************** COMPANY ******************** -->
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv parent"><spring:message
						code="master.page.company" /></a>
				<ul>
					<li><a class="child" href="application/company/list.do"><spring:message
								code="master.page.company.applications" /></a></li>
					<li><a class="child" href="order/company/list.do"><spring:message
								code="master.page.company.orders" /></a></li>
					<li><a class="child" href="finder/company/edit.do"><spring:message
								code="master.page.company.finder" /></a></li>
					<li><a class="child" href="inventory/company/list.do"><spring:message
								code="master.page.company.inventory" /></a></li>

				</ul></li>
		</security:authorize>

		<!-- ******************** DESIGNER ******************** -->
		<security:authorize access="hasRole('DESIGNER')">
			<li><a class="fNiv parent"><spring:message
						code="master.page.designer" /></a>
				<ul>
					<li><a class="child" href="post/designer/list.do"><spring:message
								code="master.page.designer.posts" /></a></li>
					<li><a class="child" href="sponsorship/designer/listDesigner.do"><spring:message
								code="master.page.designer.sponsorships" /></a></li>

				</ul></li>
		</security:authorize>

		<!-- ******************** PROVIDER ******************** -->
		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv parent"><spring:message
						code="master.page.provider" /></a>
				<ul>
					<li><a class="child" href="sponsorship/provider/list.do"><spring:message
								code="master.page.provider.sponsorships" /></a></li>
					<li><a class="child" href="creditcard/display.do"><spring:message
								code="master.page.provider.creditcard" /></a></li>

				</ul></li>
		</security:authorize>

		<!-- ******************** NOT AUTHENTICATED ******************** -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="about-us/index.do"><spring:message
						code="master.page.about-us" /></a></li>
			<li><a class="fNiv parent"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li><a class="child" href="company/register.do"><spring:message
								code="master.page.register.as.company" /></a></li>
					<li><a class="child" href="customer/register.do"><spring:message
								code="master.page.register.as.customer" /></a></li>
					<li><a class="child" href="designer/register.do"><spring:message
								code="master.page.register.as.designer" /></a></li>
					<li><a class="child" href="provider/register.do"><spring:message
								code="master.page.register.as.provider" /></a></li>
				</ul></li>
		</security:authorize>

		<!-- ******************** AUTHENTICATED ******************** -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv parent"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li><a class="child" href="social-profile/listMySocialProfiles.do"><spring:message
								code="master.page.profile.my.social.profiles" /></a></li>
					<li><a class="child" href="box/list.do"><spring:message
								code="master.page.messages" /></a></li>
					<security:authorize access="hasRole('PROVIDER')">
						<li><a class="child" href="provider/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('DESIGNER')">
						<li><a class="child" href="designer/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
						<li><a class="child" href="company/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('CUSTOMER')">
						<li><a class="child" href="customer/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a class="child" href="message/broadcast.do"><spring:message
									code="master.page.message.broadcast" /></a></li>
						<li><a class="child" href="administrator/edit.do"><spring:message
									code="master.page.profile.edit.my.profile" /></a></li>
					</security:authorize>

					<li><a class="child" href="anonymous/out.do"><spring:message
								code="master.page.anonymous" /></a></li>
					<li><a class="child" href="actor/export-data/export.do"><spring:message
								code="master.page.export.data" /></a></li>

					<li><a class="child" href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
			<li><a class="fNiv" href="about-us/index.do"><spring:message
						code="master.page.about-us" /></a></li>
		</security:authorize>

		<!-- ************************** ALL ************************ -->
		<security:authorize access="permitAll()">
			<li><a class="fNiv" href="company/list.do"><spring:message
						code="master.page.companies" /></a></li>
		</security:authorize>

		<security:authorize access="permitAll()">
			<li><a class="fNiv" href="post/list.do"><spring:message
						code="master.page.posts" /></a></li>
		</security:authorize>

	</ul>
</div>


