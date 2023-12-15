<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<%
%>
<jsp:include page="../template/header.jsp" />

<div class="col-md-12 grid-margin stretch-card">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">Coupures</h4>
            <form action="${pageContext.request.contextPath}/getCoupures" method="post">

                <div class="form-group">
                    <label>Date</label>
                    <input  class="form-control"  type="date" name="dates" placeholder="prenom">
                </div>
                <input type="submit" class="btn btn-secondary me-2" value="voir les coupures">
            <%--    <button type="submit" class="btn btn-primary me-2">voir les coupures</button>--%>

            </form>

<%--            <form class="forms-sample" action="${pageContext.request.contextPath}/getCoupures" method="post">--%>
<%--                <input type="date" name="dates">--%>
<%--                <input type="submit" value="voir les coupures">--%>
<%--            </form>--%>

        </div>
    </div>
</div>

<jsp:include page="../template/footer.jsp" />

<%--<script>--%>
<%--    function sendData(event){--%>
<%--        var formDataArray = $('#form').serializeArray();--%>

<%--        var formData = new FormData();--%>

<%--        $.each(formDataArray, function(index, field) {--%>
<%--            formData.append(field.name, field.value);--%>
<%--        });--%>

<%--        $.ajax({--%>
<%--            type: 'POST',--%>
<%--            url: '/saveEtudiant',--%>
<%--            data: formData,--%>
<%--            contentType: false,--%>
<%--            processData: false,--%>
<%--            success: function(response) {--%>
<%--                console.log('Succès:', response);--%>
<%--            },--%>
<%--            error: function(error) {--%>
<%--                console.error('Erreur:', error);--%>
<%--            }--%>
<%--        })--%>
<%--    }--%>

<%--    $(document).ready(function() {--%>
<%--        $(document).submit(function(event) {--%>
<%--            event.preventDefault();--%>
<%--            sendData(event);--%>
<%--        });--%>
<%--    });--%>

<%--</script>--%>

