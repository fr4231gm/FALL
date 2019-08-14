<%--
 * select.tag
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="value" required="false" %>

<jstl:if test="${value == null}">
	<jstl:set var="value" value="false" />
</jstl:if>


<%-- Definition --%>
<div>
 	<form:label path="${path}"> <spring:message code="${code}" /> </form:label>	
 	 

  	<div class="toggle">
	<jstl:if test="${value == true}">
    <input type="checkbox" class="check-checkbox" id="${path}" name="${path}" value="true" checked="checked">
  </jstl:if>
  <jstl:if test="${value == false}">
    <input type="checkbox" class="check-checkbox" id="${path}" name="${path}" value="true">
  </jstl:if>
    <label class="check-label" for="${path}">
    <jstl:if test="${true}">
      <div class="background2"></div>
	</jstl:if>
      <span class="face">
        <span class="face-container">
          <span class="eye left"></span>
          <span class="eye right"></span>
          <span class="mouth"></span>
        </span>
      </span>
    </label>
  </div>

                    <form:errors path="${path}" cssClass="error" /> 
        </div>


