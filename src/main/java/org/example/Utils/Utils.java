package org.example.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.DAO.UserDAO;
import org.example.Entities.Recomendacion;
import org.example.Entities.Usuario;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class Utils {


    public static boolean esCorreoGmailValido(String correo) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return correo.matches(regex);
    }

    public static String hashPassword(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(pass.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static void generarReportePDF(List<Usuario> users, List<String> recomendaciones) throws DocumentException, IOException {
        UserDAO userDAO = new UserDAO();
        // Obtener la ruta del escritorio de 'washi'
        String escritorioRuta = System.getProperty("user.home") + "/Desktop/ReporteImpacto.pdf";

        // Crear un documento PDF
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(escritorioRuta));

        // Abrir el documento para agregar contenido
        document.open();

        // Agregar un título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reporte de Impacto por Categoría", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Espacio para separar el título del contenido
        document.add(new Paragraph("\n"));

        // Agregar la información de cada usuario
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        for (Usuario u : users) {
            // Calcular el impacto por categoría para cada usuario
            Map<String, BigDecimal> impactoPorCategoria = userDAO.calcularImpactoPorCategoriaPorIDUsuario(u);

            // Agregar el nombre del usuario
            document.add(new Paragraph("Usuario: " + u.getNombre(), contentFont));
            document.add(new Paragraph("Impacto por Categoría:", contentFont));

            // Agregar el impacto por categoría
            for (Map.Entry<String, BigDecimal> entry : impactoPorCategoria.entrySet()) {
                String categoria = entry.getKey();
                BigDecimal impacto = entry.getValue();
                document.add(new Paragraph("  - " + categoria + ": " + impacto.toString(), contentFont));
            }

            // Espacio entre usuarios
            document.add(new Paragraph("\n"));
        }

        // Agregar recomendaciones
        document.add(new Paragraph("Recomendaciones:", titleFont));
        for (String recomendacion : recomendaciones) {
            document.add(new Paragraph("- " + recomendacion, contentFont));
        }

        // Cerrar el documento
        document.close();

        System.out.println("El PDF se ha guardado en el escritorio de 'washi'.");
    }


}





