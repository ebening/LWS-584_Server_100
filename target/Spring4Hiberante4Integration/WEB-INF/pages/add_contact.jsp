<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <jsp:include page="template/head.jsp" />
    
</head>

<body>


	<div id="wrapper">

		<jsp:include page="template/nav_superior.jsp" />

		<!-- Page Content -->
		<div id="page-wrapper">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Plantilla...</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
            
           
       <button id="addB" type="button"  class="btn-primary">add row</button>

<form:form method="post" action="save.html" modelAttribute="contactForm">
    <table id="tabla_">
    <tr>
        <th>No.</th>
        <th>Name</th>
        <th>Lastname</th>
        <th>Email</th>
        <th>Phone</th>
        <th></th>
    </tr>
    <c:forEach items="${contactForm.contacts}" var="contact" varStatus="status">
        <tr>
            <td align="center">${status.count}</td>
            <td><form:input path="contacts[${status.index}].firstname"  value="${contact.firstname}"/></td>
            <td><form:input path="contacts[${status.index}].lastname"   value="${contact.lastname}"/></td>
            <td><form:input path="contacts[${status.index}].email"      value="${contact.email}"/></td>
            <td><form:input path="contacts[${status.index}].phone"      value="${contact.phone}"/></td>
            <td>
              <form:select path="contacts[${status.index}].locacion" cssClass="form-control">
              						<option value="-1">se</option>
									<c:forEach items="${lstLocaciones}" var="llist">
										<option ${contact.locacion.idLocacion == llist.idLocacion ? 'selected' : ''} value="${llist.idLocacion}">${llist.descripcion} </option>
									</c:forEach>
			  </form:select>
            </td>
        </tr>
    </c:forEach>
</table>  
<br/>
<input type="submit" value="Save" />
     
</form:form>
           
            
        </div>
        <!-- /#page-wrapper -->
    </div>
    
  <jsp:include page="template/includes.jsp" />
  
         <script>
			$(document).ready(function() {

				$("#addB").click(function() {
					
					loading(true);
					$.ajax({
						type : "GET",
						cache : false,
						url : "${pageContext.request.contextPath}/getContactR2",
						async : true,
     	                data: "numrows=2",
						success : function(response) {
							loading(false);
							$("#tabla_").append(response);
						},
						error : function(e) {
							loading(false);
							console.log('Error: ' + e);
						},
					});
				});

			});
		</script>

</body>
</html>
