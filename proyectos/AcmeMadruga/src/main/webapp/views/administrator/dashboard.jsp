<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMINISTRATOR')">

<%-- MEMBERS PER AREA --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.members.per.brotherhood" /></th>
		<td><jstl:out value="${memberPerBrotherhoodStats[0][0]}" /></td>
		<td><jstl:out value="${memberPerBrotherhoodStats[0][1]}" /></td>
		<td><jstl:out value="${memberPerBrotherhoodStats[0][2]}" /></td>
		<td><jstl:out value="${memberPerBrotherhoodStats[0][3]}" /></td>
	</tr>
</table>

<%-- LARGEST BROTHERHOODS --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.first" /></th>
		<th><spring:message code="administrator.dashboard.second" /></th>
		<th><spring:message code="administrator.dashboard.third" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.largest.brotherhoods" /></th>
		<td><jstl:out value="${largestBrotherhoods[0].title}" /></td>
		<td><jstl:out value="${largestBrotherhoods[1].title}" /></td>
		<td><jstl:out value="${largestBrotherhoods[2].title}" /></td>
	</tr>
</table>

<%-- SMALLEST BROTHERHOODS --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.first" /></th>
		<th><spring:message code="administrator.dashboard.second" /></th>
		<th><spring:message code="administrator.dashboard.third" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.smallest.brotherhoods" /></th>
		<td><jstl:out value="${smallestBrotherhoods[0].title}" /></td>
		<td><jstl:out value="${smallestBrotherhoods[1].title}" /></td>
		<td><jstl:out value="${smallestBrotherhoods[2].title}" /></td>
	</tr>
</table>

<%-- REQUESTS TO MARCH ON A PROCESSION --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.request.pending" /></th>
		<th><spring:message code="administrator.dashboard.request.approved" /></th>
		<th><spring:message code="administrator.dashboard.request.rejected" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.ratio.request" /></th>
		<td><jstl:out value="${ratioPendingRequests}" /></td>
		<td><jstl:out value="${ratioApprovedRequests}" /></td>
		<td><jstl:out value="${ratioRejectedRequests}" /></td>
	</tr>
</table>

<%-- PROCESSIONS IN 30 DAYS OR LESS --%>
<table>
	<tr>
		<th><spring:message
				code="administrator.dashboard.following.processions" /></th>
		
		<jstl:forEach 
			items="${processionsOrganizedLess30Days}"
			var = "row">
			<td><jstl:out value="${row.title}" /></td>
		</jstl:forEach>
	</tr>
</table>

<%-- REQUESTS TO MARCH FOR EACH PROCESSION --%>
<table>
	<tr>
		<th><spring:message	code="administrator.dashboard.ratio.request.procession" /></th>
		<th><spring:message code="administrator.dashboard.request.identity" /></th>
		<th><spring:message code="administrator.dashboard.request.pending" /></th>
		<th><spring:message code="administrator.dashboard.request.approved" /></th>
		<th><spring:message code="administrator.dashboard.request.rejected" /></th>
	</tr>
		
		<jstl:forEach 
			items="${processionAndRatiosPendingApprovedRejected}"
			var = "row">
			<tr>
				<td></td>
				<td><jstl:out value="${row[0]}" /></td>
				<td><jstl:out value="${row[1]}" /></td>
				<td><jstl:out value="${row[2]}" /></td>
				<td><jstl:out value="${row[3]}" /></td>
			</tr>
		</jstl:forEach>
	
</table>

<%-- MEMBERS WITH 10% MORE MAXIMUM --%>
<table>
	<tr>
		<th><spring:message
				code="administrator.dashboard.members.approved" /></th>
		
		<jstl:forEach 
			items="${members10PercentMaximumRequestAccepted}"
			var = "row">
			<td><jstl:out value="${row.name}" /></td>
		</jstl:forEach>
	</tr>
</table>

<%-- HISTOGRAM OF POSITIONS --%>

<table>
	<tr>
		<th><spring:message	code="administrator.dashboard.histogram.positions" /></th>
		<th><spring:message	code="administrator.dashboard.histogram.positions.position" /></th>
		<th><spring:message	code="administrator.dashboard.histogram.positions.value" /></th>
	</tr>
	<jstl:forEach 
			items="${positionHistogram}"
			var = "row">
			<tr>
			<jstl:if test="${language=='en'}">
				<td></td>
				<td><jstl:out value="${row[0].name['en']}" /></td>
			</jstl:if>
			<jstl:if test="${language=='es'}">
				<td></td>
				<td><jstl:out value="${row[0].name['es']}" /></td>
			</jstl:if>
				<td><jstl:out value="${row[1]}" /></td>	
		
	</tr>
	</jstl:forEach>
</table>

<%-- BROTHERHOODS PER AREA --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.ratio" /></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.brotherhoods.per.area" /></th>
		<td><jstl:out value="${ratioMinMaxAvgDesvBrotherhoodsPerArea[0]}" /></td>
		<td><jstl:out value="${ratioMinMaxAvgDesvBrotherhoodsPerArea[1]}" /></td>
		<td><jstl:out value="${ratioMinMaxAvgDesvBrotherhoodsPerArea[2]}" /></td>
		<td><jstl:out value="${ratioMinMaxAvgDesvBrotherhoodsPerArea[3]}" /></td>
		<td><jstl:out value="${ratioMinMaxAvgDesvBrotherhoodsPerArea[4]}" /></td>
		<td><jstl:out value="${ratioMinMaxAvgDesvBrotherhoodsPerArea[5]}" /></td>
		
	</tr>
</table>
<table>
<tr>
	<th><spring:message code="administrator.dashboard.brotherhoods.per.area" /></th>
	<th><spring:message	code="administrator.dashboard.count.area" /></th>
	<th><spring:message	code="administrator.dashboard.count" /></th>
</tr>

<jstl:forEach 
			items="${countBrotherhoodsPerArea}"
			var = "row">
			<tr>
				<td></td>
				<td><jstl:out value="${row[0]}" /></td>
				<td><jstl:out value="${row[1]}" /></td>
			</tr>
</jstl:forEach>	
</table>

<%-- FINDER RESULTS --%>
<table>
	<tr>
		<th></th>
		<th><spring:message code="administrator.dashboard.minimum" /></th>
		<th><spring:message code="administrator.dashboard.maximum" /></th>
		<th><spring:message code="administrator.dashboard.average" /></th>
		<th><spring:message code="administrator.dashboard.deviation" /></th>
	</tr>
	<tr>
		<th><spring:message
				code="administrator.dashboard.finder.results" /></th>
		<td><jstl:out value="${minMaxAvgDesvResultsPerFinder[0][0]}" /></td>
		<td><jstl:out value="${minMaxAvgDesvResultsPerFinder[0][1]}" /></td>
		<td><jstl:out value="${minMaxAvgDesvResultsPerFinder[0][2]}" /></td>
		<td><jstl:out value="${minMaxAvgDesvResultsPerFinder[0][3]}" /></td>
	</tr>
</table>

<%-- EMPTY vs NOT EMPTY FINDERS --%>
<table>
	<tr>
		<th><spring:message code="administrator.dashboard.finder.empty" /></th>
		<td><jstl:out value="${ratioEmptyvsNonEmptyFinders}" /></td>
	</tr>
</table>

<canvas id="myChart" width="800" height="200"></canvas>


    <canvas id="myChart"></canvas>

  
  	<canvas id="chartOneContainer" width="800" height="200"></canvas>
	<br />
	<canvas id="chartTwoContainer" width="800" height="200"></canvas>
	<br />
	<canvas id="chartThreeContainer" width="800" height="200"></canvas>

  <script>
    let myChart = document.getElementById('myChart').getContext('2d');

    // Global Options
    Chart.defaults.global.defaultFontFamily = 'Comic Sans MS';
    Chart.defaults.global.defaultFontSize = 32;
    Chart.defaults.global.defaultFontColor = '#000';
	
    let massPopChart = new Chart(myChart, {
      type:'pie', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
      data:{
        labels:['<spring:message code="administrator.not.spammers" />', '<spring:message code="administrator.spammers" />'],
        datasets:[{
          label:'Shouts',
          data:["${spammersRatio}","${1 - spammersRatio}"],
          backgroundColor:[
            'rgba(050, 050, 050, 0.7)',
            'rgba(255, 0, 255, 0.7)'
          ],
          borderWidth:2,
          borderColor:'#333',
          hoverBorderWidth:4,
          hoverBorderColor:'#000',
        }]
      },
      options:{
        legend:{
          display:false,
          position:'right',
          labels:{
            fontColor:'#000'
          }
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        },
        layout:{
          padding:{
            left:50,
            right:0,
            bottom:0,
            top:0
          }
        },
        tooltips:{
          enabled:true
        }
      }
    });
  </script>
  
  <script>
  var optionsOne = {
		  type: 'pie',
		  data: {
		    labels: ["Polarity"],
		    datasets: [ 
		            {
		                label: 'Colors One',
		                data: ["${polarityAverage}"],
		                borderWidth: 1
		            }
		        ]
		  },
		  options: {
		    scales: {
		        xAxes: [{
		        ticks: {
		                    display: true
		        }
		      }]
		    }
		  }
		}

  var optionsTwo = {
		  type: 'bar',
		  data: {
		    labels: ["<spring:message code='administrator.ratio'/>", "<spring:message code='administrator.min'/>", "<spring:message code='administrator.max'/>", "<spring:message code='administrator.avg'/>"],
		    datasets: [ 
		            {
		                label: '<spring:message code="administrator.dashboard.brotherhoods.per.area" />',
		                data: ["${ratioMinMaxAvgDesvBrotherhoodsPerArea[0]}","${ratioMinMaxAvgDesvBrotherhoodsPerArea[1]}","${ratioMinMaxAvgDesvBrotherhoodsPerArea[2]}","${ratioMinMaxAvgDesvBrotherhoodsPerArea[3]}"],
		                borderWidth: 1
		            }
		        ]
		  },
		  options: {
		    scales: {
		        xAxes: [{
		        ticks: {
		                    display: true
		        }
		      }]
		    }
		  }
		}
 
  var optionsThree = {
		  type: 'bar',
		  data: {
		    labels: ["<spring:message code='administrator.avg'/>", "<spring:message code='administrator.min'/>", "<spring:message code='administrator.max'/>", "<spring:message code='administrator.stddev'/>"],
		    datasets: [ 
		            {
		                label: '<spring:message code="administrator.dashboard.members.per.brotherhood" />',
		                data: ["${memberPerBrotherhoodStats[0][2]}","${memberPerBrotherhoodStats[0][0]}","${memberPerBrotherhoodStats[0][1]}","${memberPerBrotherhoodStats[0][3]}"],
		                borderWidth: 1
		            }
		        ]
		  },
		  options: {
		    scales: {
		        xAxes: [{
		        ticks: {
		                    display: true
		        }
		      }]
		    }
		  }
		}
	

		var ctxOne = document.getElementById('chartOneContainer').getContext('2d');
		new Chart(ctxOne, optionsOne);
  
		var ctxTwo = document.getElementById('chartTwoContainer').getContext('2d');
		new Chart(ctxTwo, optionsTwo);
		
		var ctxThree = document.getElementById('chartThreeContainer').getContext('2d');
		new Chart(ctxThree, optionsThree);
  </script>

  





</security:authorize>