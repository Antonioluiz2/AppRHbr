package com.AppRH.AppRH.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;
import com.AppRH.AppRH.repositories.CandidatoRepository;
import com.AppRH.AppRH.services.CandidatoService;
import com.AppRH.AppRH.services.VagaService;

import jakarta.validation.Valid;

@Controller
@RestController(value = "/")
@CrossOrigin(origins = "*")
public class VagaController {

	@Autowired
	private VagaService vs;
	
	@Autowired
	private CandidatoService cs;
	
	@Autowired
	private CandidatoRepository cr;
//	
//	@Autowired
//	private VagaRepository vr;


	
	@GetMapping("/formVaga")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("vaga/formVaga");
		return mv;
	}

	
	
	@PostMapping("/cadastrarVagas")
	private ModelAndView  Save(@Valid Vaga vaga, BindingResult br, RedirectAttributes attributes) {
		ModelAndView mv= new ModelAndView();
		if(br.hasErrors()) {
			mv.setViewName("vaga/formVaga");
			attributes.addAttribute("Menssagem", "Verefique os campos");
			return mv;
		}else {
			vs.cadastrar(vaga);
			attributes.addFlashAttribute("mensagem", "Vaga Cadastrada com Sucesso!");
			mv.setViewName("redirect:/listaVaga");
			
		}
		return mv;
	}

	// LISTAR VAGAS
	@GetMapping ("/listaVaga")
	public ModelAndView listaVagas() {
		ModelAndView mv = new ModelAndView("vaga/listaVagas");
		Iterable<Vaga> vagas = vs.listTodos();
		mv.addObject("vagas", vagas);
		return mv;
	}
	@GetMapping(value = "/{codigo}")
	public ModelAndView detalhesVaga(@PathVariable("codigo") long codigo) {
		Vaga vaga = vs.detalhesVaga(codigo);
		ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
		mv.addObject("vaga", vaga);
		//mv.setViewName("redirect:/detalhesVaga");
		Iterable<Candidato> canditados =  cr.findByVaga(vaga);
		mv.addObject("candidatos", canditados);
		return mv;
	}

	//LISTA POR NOME
	@GetMapping(value="/list/{nome}")
	private ModelAndView listNomes(@RequestParam String nome){
		ModelAndView mv = new ModelAndView("vaga/buscaPorNome");
		List<Vaga> lista=vs.buscarPorNome(nome);
		mv.addObject("vagas", lista);
//		mv.setViewName("vaga/buscaPorNome");
//		mv.setViewName("redirect:/listaVaga");
		return mv;
	}
	
	//LISTA TODOS
	@GetMapping("/lista")
	private List<Vaga> list(){
		List<Vaga> lista=vs.listTodos();
		return lista;
	}
	
	@GetMapping("/pegar/{codigo}")
	public Vaga pegarPorId(@PathVariable("codigo") long codigo, @RequestBody Vaga vaga) {
		return vs.buscarPorId(codigo);

	}

	// DELETA VAGA
	@GetMapping(value="/deletarVaga/{codigo}")
	public ModelAndView deletarVaga(@PathVariable long codigo, RedirectAttributes attributes ) {
		ModelAndView mv = new ModelAndView();
		try {
			Vaga vg=vs.buscarPorId(codigo);
			if(vg==null) {
				attributes.addFlashAttribute("mensagem", "ID não existe");
				return mv;
			}else {
				vs.delete(codigo);
				attributes.addFlashAttribute("mensagem", "VAGA "+""+vg.getNome()+" - "+vg.getCodigo()+""+" EXCLUIDA");
				mv.setViewName("redirect:/listaVaga");
				return mv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	// ADICIONAR CANDIDATO
	@PostMapping("/{codigo}")
	public ModelAndView detalhesVagaPost(@PathVariable("codigo") long codigo, @Valid Candidato candidato,
			BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv= new ModelAndView();
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			mv.setViewName("redirect:/{codigo}");
			return mv;
		}

		// rg duplicado
		if (cs.buscaPorRg(candidato.getRg()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "RG duplicado");
			mv.setViewName("redirect:/{codigo}");
			return mv;
		}

		Vaga vaga = vs.buscarPorId(codigo);
		candidato.setVaga(vaga);
		cs.cadastrar(candidato);
		attributes.addFlashAttribute("mensagem", "Candidato adionado com sucesso!");
		mv.setViewName("redirect:/{codigo}");
		return mv;
//		return "redirect:/pst/{codigo}";
	}

	// DELETA CANDIDATO pelo RG
	@GetMapping("/deletarCandidato")
	public ModelAndView deletarCandidato(String rg) {
		Vaga vg=new Vaga();
		
		ModelAndView mv=new ModelAndView();
		Vaga vaga = vs.detalhesVaga(vg.getCodigo());
		cs.delete(rg);
		mv.setViewName("redirect:/listaVaga");
		mv.addObject("vaga", vaga);
		//mv.setViewName("redirect:/detalhesVaga");
		Iterable<Candidato> canditados =  cr.findByVaga(vaga);
		mv.addObject("candidatos", canditados);
		return mv;

	}

	// Métodos que atualizam vaga
	@GetMapping(value = "/editarVaga")
	public ModelAndView editarVaga(long codigo) {
		Vaga vaga = vs.buscarPorId(codigo);
		ModelAndView mv = new ModelAndView("vaga/update-vaga");
		mv.addObject("vaga", vaga);
		return mv;
	}
//
//	// UPDATE vaga
	@PostMapping(value = "/editarVaga")
	public ModelAndView updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv=new ModelAndView("vaga/detalhesVaga");
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");
		
		long codigoLong = vaga.getCodigo();
		String codigo = "" + codigoLong;
		mv.addObject("vaga", vaga);
		return mv;
	}

	//LISTA TODOS
		@GetMapping("/listaCand")
		private List<Candidato> listCand(){
			List<Candidato> lista=cs.listCand();
			return lista;

		}

}
