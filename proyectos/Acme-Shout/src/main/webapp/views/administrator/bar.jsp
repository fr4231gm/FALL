<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<canvas id="myChart" width="800" height="200"></canvas>


  <div class="container">
    <canvas id="myChart"></canvas>
  </div>

  <script>
    let myChart = document.getElementById('myChart').getContext('2d');

    // Global Options
    Chart.defaults.global.defaultFontFamily = 'Comic Sans MS';
    Chart.defaults.global.defaultFontSize = 32;
    Chart.defaults.global.defaultFontColor = '#000';
	
    let massPopChart = new Chart(myChart, {
      type:'bar', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
      data:{
        labels:['<spring:message code="administrator.count.short.shouts" />', '<spring:message code="administrator.count.all.shouts" />', '<spring:message code="administrator.count.long.shouts" />'],
        datasets:[{
          label:'Shouts',
          data:["${statistics.get('count.short.shouts')}","${statistics.get('count.all.shouts')}","${statistics.get('count.long.shouts')}"],
          backgroundColor:[
            'rgba(050, 050, 050, 0.7)',
            'rgba(128, 128, 128, 0.7)',
            'rgba(255, 255, 255, 0.7)'
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
