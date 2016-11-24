<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.awt.*"%>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.*" %>
<%
String[] imageWidth = request.getParameterValues("width");
String[] imageHeight = request.getParameterValues("height");
int wInt = Integer.parseInt(imageWidth[0]);
int hInt = Integer.parseInt(imageHeight[0]);
Color background = new Color(Integer.parseInt("ffffff",16));
BufferedImage buffer = new BufferedImage(wInt, hInt, BufferedImage.TYPE_INT_RGB);
Graphics g = buffer.createGraphics();
g.setColor(background);
g.fillRect(0, 0, wInt, hInt);

for(int y=0; y<hInt; y++) {
    int x = 0;
    String[] rows = request.getParameterValues("r"+y);
    String[] row = rows[0].split(",");
    for(int j=0; j<row.length; j++){
        String[] pixel = row[j].split(":");
        Color pixelColor = new Color(Integer.parseInt(pixel[0],16));
        int repeat = 1;
        if(pixel.length>1)
            repeat = Integer.parseInt(pixel[1]);
        for(int k=0; k<repeat; k++){
            g.setColor(pixelColor);
            g.fillRect(x, y, 1, 1);
            x++;
        }
    }
}

response.setContentType("image/png");
response.setHeader ("Content-Disposition", "attachment; filename=\"chart.png\"");
OutputStream os = response.getOutputStream();
ImageIO.write(buffer, "png", os);
os.close();
%>
 