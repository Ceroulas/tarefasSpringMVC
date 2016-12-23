<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<script type='text/javascript' src='resources/js/jquery-1.4.2.min.js'></script>
	<script type='text/javascript' src='resources/js/jquery-ui-1.8.2.custom.min.js'></script>
</head>
<body>
	<script type="text/javascript">
		function deletaAgora(id){
			$.post("removeTarefa?id="+id, function(response){
				alert("Tarefa removida!");
				$("#tarefa"+id).hide();
			});
		}
		
		function finalizaAgora(id){
			$.get("finalizaTarefa?id="+id, function(response){
				alert("Tarefa finalizada!");
				$("#tarefa_"+id).html("Finalizado");
			});
		}
	</script>
  <a href="novaTarefa">Criar nova tarefa</a> 

  <br /> <br />        

  <table>
  <tr>
    <th>Id</th>
    <th>Descrição</th>
    <th>Finalizado?</th>
    <th>Data de finalização</th>
  </tr>
  <c:forEach items="${tarefas}" var="tarefa">
    <tr id="tarefa${tarefa.id}">
      <td>${tarefa.id}</td>
      <td>${tarefa.descricao}</td>
      <c:if test="${not tarefa.finalizado}">
        <td id="tarefa_${tarefa.id}">
      		<a href="#" onClick="finalizaAgora(${tarefa.id})">Finalizar agora</a>
      	</td>
      </c:if>
      <c:if test="${tarefa.finalizado eq true}">
        <td>Finalizado</td>
      </c:if>
      <td>
        <fmt:formatDate 
          value="${tarefa.dataFinalizacao.time}" 
          pattern="dd/MM/yyyy"/>
      </td>
      <td>
      	<a href="#" onClick="deletaAgora(${tarefa.id})">Remover</a>
      </td>
      <td>
      	<a href="mostraTarefa?id=${tarefa.id}">Editar</a>
      </td>
    </tr>
  </c:forEach>
  </table>
</body>
</html>