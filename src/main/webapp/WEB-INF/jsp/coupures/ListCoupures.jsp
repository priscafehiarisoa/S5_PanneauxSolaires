<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="panneauxsolaires.model.objects.Coupures" %>
<%--
  Created by IntelliJ IDEA.
  User: priscafehiarisoadama
  Date: 08/12/2023
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Coupures> liste= (List<Coupures>) request.getAttribute("coupures");
%>

<jsp:include page="../template/header.jsp" />
<h4 class="card-title">Liste des coupures</h4>


            <%for (int j = 0; j < liste.size(); j++) {%>
<div class="col-lg-12 grid-margin stretch-card">
    <div class="card">
        <div class="card-body">
           <p>seteur : <span class="card-title"> <%=liste.get(j).getSecteur().getNomPanneaux()%></span> </p>

            <span><%=liste.get(j).getDate()%></span>
            <hr>
                <div class="m-5">

                    <%--     à boucler       --%>
                    <div>
                        <h5>consommation par eleve : <%=liste.get(j).getConsommationParEleve()%> </h5>
                        <h5>heure de coupure : <%=liste.get(j).getHeureCoupure()%></h5>
                        <h5>50% batt : <%=liste.get(j).getSecteur().getCapaciteBatterie()/2%></h5>
                        <h5>salles  : </h5>
                        <ul>
                            <%for(int i = 0; i < liste.get(j).getListeSalle().size(); i++) {%>
                            <li><%=liste.get(j).getListeSalle().get(i).getNomSalle()%></li>
                            <% }%>
                        </ul>
                    </div>
                    <div class="table-responsive">
                        <h3>detail des coupures </h3>
                        <table class="table">
                            <tr>
                                <th>consommation</th>
                                <th>nombre d'eleves</th>
                                <th>heure</th>
                                <th>luminosite</th>
                                <th>puissance reçus panneaux</th>
                                <th>reste Batteriie</th>
                                <th>surplus</th>
                                <th>coupures?</th>

                            </tr>
                            <%for(int i = 0; i < liste.get(j).getDetailCoupures().size(); i++) {%>

                            <tr>
                                <td><%=liste.get(j).getDetailCoupures().get(i).getConsommation()%></td>
                                <td><%=liste.get(j).getDetailCoupures().get(i).getNombresEleves()%></td>
                                <td><%=liste.get(j).getDetailCoupures().get(i).getLuminosite().getDebutLuminosite()%></td>
                                <td><%=liste.get(j).getDetailCoupures().get(i).getLuminosite().getNiveauLuminosite()%></td>
                                <td><%=liste.get(j).getDetailCoupures().get(i).getPuissancePanneaux()%></td>
                                <td><%=liste.get(j).getDetailCoupures().get(i).getResteBatterie()%></td>
                                <td><%=liste.get(j).getDetailCoupures().get(i).getSurplus()%></td>
                                <td><%=liste.get(j).getDetailCoupures().get(i).isCoupure()%></td>

                            </tr>
                            <%}%>
                        </table>

                    </div>
                </div>
                </div>
    </div>
</div>
            <%}%>
<%--        fin boucle --%>

<jsp:include page="../template/footer.jsp" />

