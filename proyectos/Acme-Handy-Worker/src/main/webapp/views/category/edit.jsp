
<%-- edit.jsp fix-up task --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="category">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="name">
											<spring:message code="category.name" />:&nbsp;
										</form:label>
										<form:input path="name" value="${category.name}" placeholder="Bathroom" />
										<form:errors cssClass="error" path="name" />
									</div>
								</div>
									<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="nombre">
											<spring:message code="category.nombre" />:&nbsp;
										</form:label>
										<form:input path="nombre" value="${category.nombre}" placeholder="Cuarto baño" />
										<form:errors cssClass="error" path="nombre" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt">
									<div class="input-field">
										<form:label path="parentCategory">
											<spring:message code="category.parentCategory" />:
										</form:label>
											<form:select id="categories" path="parentCategory">

												<form:options items="${parentCategories}" itemValue="id" itemLabel="name" />
									</form:select>
										<form:errors cssClass="error" path="parentCategory" />
											<br />

									</div>
								</div>
								<div class="col-xs-12">
									<input type="submit" name="save"
										value="<spring:message code="category.save"/>" />
									
									<input
										type="button" name="cancel"
										value="<spring:message code='category.cancel'/>"
										onclick="javascript: relativeRedir('${cancelURI}');" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>















