package com.tidsec.gestioncursos.report;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.tidsec.gestioncursos.entidades.Curso;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CursoExporterPDF
{
    private List<Curso> listaCursos;

    public CursoExporterPDF(List<Curso> listaCursos)
    {
        this.listaCursos = listaCursos;
    }
    private void writeTableHeader(PdfPTable table) // metodo para introducir la cabecera al pdf
    {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Titulo",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Descripcion",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nivel",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Publicado",font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) // metodo para ingresar los datos de el objeto
    {
        for(Curso curso : listaCursos)
        {
            table.addCell(String.valueOf(curso.getId())); //valueOf es para convertir entero a string
            table.addCell(curso.getTitulo()); // añade a cada celda el dato del obeto curso
            table.addCell(curso.getDescripcion());
            table.addCell(String.valueOf(curso.getNivel()));
            table.addCell((String.valueOf(curso.isPublicado())));
        }
    }

    public void export(HttpServletResponse response) throws IOException  //Metodo para exportacion
    {
        Document document = new Document(PageSize.A4); //Objeto del tipo documento con tamaño A4
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open(); // abrimos el documento

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD); // Esta es la fuente
        font.setSize(18); //Tamaño de la fuente
        font.setColor(Color.BLUE); // Color de la fuente

        Paragraph p = new Paragraph("Lista de cursos",font); // objeto del tipo Parrafo con fondo
        p.setAlignment(Paragraph.ALIGN_CENTER); // el parrafo estara alineado al centro

        document.add(p); //añadimos el obejto p en el documento

        PdfPTable table = new PdfPTable(5); // cuantas columnas tiene la tabla
        table.setWidthPercentage(100f); // damos el porcentaje de la tabla
        table.setWidths(new float[]{1.3f,3.5f,3.5f,2.0f,1.5f}); //damos el ancho de tippo float de cada columna de acuerdo al dato
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}
