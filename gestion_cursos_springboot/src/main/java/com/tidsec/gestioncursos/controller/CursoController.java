package com.tidsec.gestioncursos.controller;

import com.tidsec.gestioncursos.entidades.Curso;
import com.tidsec.gestioncursos.report.CursoExporterExcel;
import com.tidsec.gestioncursos.report.CursoExporterPDF;
import com.tidsec.gestioncursos.repositorio.ICursoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List; // classe para llamar al metodo list

@Controller // se especifica que esta clase es controlador
public class CursoController
{
   @Autowired // inyeccion de dependencia de Interfaz ICursoRepository
   private ICursoRepository cursoRepository;

   @GetMapping // obtiene la pagina principal que es cursos
   public String home()
   {
       return "redirect:/cursos";
   }
   @GetMapping("/cursos") // el mentodo enlista los curso y muestra la pagina cursos
   public String listarCursos(Model model)
   {
      List<Curso> cursos = cursoRepository.findAll();
      cursos = cursoRepository.findAll();
      model.addAttribute("cursos",cursos);
      return "cursos";
   }
   @GetMapping("/cursos/nuevo") // obtiene la pagina nuevo curso con el formulario FrmCurso
   public String AgregarCurso(Model model)
   {
      Curso curso = new Curso();
      curso.setPublicado(true);
      model.addAttribute("curso",curso);
      model.addAttribute("pageTitulo","Nuevo curso");
      return "FrmCurso";
   }
   @PostMapping("/cursos/save") // este metodo post agrega un nuevo curso
   public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes)
   {
      try
      {
         cursoRepository.save(curso);
         redirectAttributes.addFlashAttribute("message","El curso se agrego correctamente");
      }
      catch(Exception e)
      {
         redirectAttributes.addAttribute("message",e.getMessage());
      }
      return "redirect:/cursos";
   }
   @GetMapping("/cursos/{id}") // es un endpoint que se muestra en el navefador con el formulario y el numero de id
   public String EditarCurso(@PathVariable Integer id,Model model,RedirectAttributes redirectAttributes)
   {
      try
      {
         Curso curso = cursoRepository.findById(id).get();
         //redirectAttributes.addFlashAttribute("message","Curso modificado correctamente");
         model.addAttribute("curso",curso);
         model.addAttribute("pageTitulo","Editar curso: "+ id);
         return "FrmCurso";
      }
      catch(Exception e)
      {
         redirectAttributes.addFlashAttribute("message",e.getMessage());
      }
      return "redirect:/cursos";
   }
   @GetMapping("/cursos/delete/{id}") // obiene el end point y el id del curso que se elmino
   public String EliminarCurso(@PathVariable Integer id,Model model, RedirectAttributes redirectAttributes)
   {
      try
      {
         cursoRepository.deleteById(id);
         redirectAttributes.addFlashAttribute("message","Curso eliminado correctamente");
      }
      catch(Exception exception)
      {
         redirectAttributes.addFlashAttribute("message", exception.getMessage());
      }
      return "redirect:/cursos";
   }
   @GetMapping("/export/pdf")
   public void GenerarReportePDF(HttpServletResponse responde) throws IOException {
      responde.setContentType("application/pdf");
      DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
      String curretDateTime = dateFormat.format(new Date());

      String headerKey = "Content-Disposition";
      String headerValue = "attachment; filename=cursos" + curretDateTime + ".pdf";
      responde.setHeader(headerKey,headerValue);

      List<Curso> cursos = cursoRepository.findAll();

      CursoExporterPDF exporterPDF = new CursoExporterPDF(cursos);
      exporterPDF.export(responde);
   }
   @GetMapping("/export/excel")
   public void GenerarReporteExcel(HttpServletResponse responde) throws IOException {
      responde.setContentType("application/octet-stream");
      DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
      String curretDateTime = dateFormat.format(new Date());

      String headerKey = "Content-Disposition";
      String headerValue = "attachment; filename=cursos" + curretDateTime + ".xlsx";
      responde.setHeader(headerKey,headerValue);

      List<Curso> cursos = cursoRepository.findAll();

      CursoExporterExcel exporterExcel = new CursoExporterExcel(cursos);
      exporterExcel.export(responde);
   }
}
