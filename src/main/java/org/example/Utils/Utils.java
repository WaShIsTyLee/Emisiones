package org.example.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.Entities.Usuario;
import org.example.Services.UserService;

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
         UserService userService = new UserService();
        String escritorioRuta = System.getProperty("user.home") + "/Desktop/ReporteImpacto.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(escritorioRuta));
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reporte de Impacto por Categoría", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        for (Usuario u : users) {
            Map<String, BigDecimal> impactoPorCategoria = userService.getFactorEmisionMap(u);

            document.add(new Paragraph("Usuario: " + u.getNombre(), contentFont));
            document.add(new Paragraph("Impacto por Categoría:", contentFont));

            for (Map.Entry<String, BigDecimal> entry : impactoPorCategoria.entrySet()) {
                String categoria = entry.getKey();
                BigDecimal impacto = entry.getValue();
                document.add(new Paragraph("  - " + categoria + ": " + impacto.toString(), contentFont));
            }

            document.add(new Paragraph("\n"));
        }

        document.add(new Paragraph("Recomendaciones:", titleFont));
        for (String recomendacion : recomendaciones) {
            document.add(new Paragraph("- " + recomendacion, contentFont));
        }
        document.close();

        System.out.println("El PDF se ha guardado en el escritorio de 'washi'.");
    }


}





