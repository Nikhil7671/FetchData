

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class FetchData extends HttpServlet {
    String username = "root";
    String password = "Root@1234";
    String url = "jdbc:mysql://localhost:3306/school";

    public void Post(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        res.setContentType("text/html");
        PrintWriter print = res.getWriter();

        int rollNo = Integer.parseInt(req.getParameter("rollNo"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from student where rollNo =? ");

            preparedStatement.setInt(1, rollNo);

            print.print("<table width=75% border=1>");
            print.print("<caption> Student Result :</caption>");

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int totalColumn = resultSetMetaData.getColumnCount();
            print.print("<tr>");
            for(int i=1;i<=totalColumn;i++){
                print.print("<th>"+resultSetMetaData.getColumnName(i)+"</th>");
            }

            print.print("</tr>");
            while (resultSet.next()){
                print.print("<tr><td>"+resultSet.getInt(1)+"</td><td>"+resultSet.getString(2)+"</td><td>"+resultSet.getString(3)+"</td><td>"+resultSet.getInt(4)+"</td></tr>");
            }
            print.print("</table>");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
