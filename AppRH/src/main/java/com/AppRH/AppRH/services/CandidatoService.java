package com.AppRH.AppRH.services;

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;
import com.AppRH.AppRH.repositories.CandidatoRepository;
import com.AppRH.AppRH.repositories.VagaRepository;

import jakarta.validation.Valid;

@Service
public class CandidatoService {
	
	@Autowired
	private CandidatoRepository cr;
	
	@Autowired
	private VagaRepository vr;
	
//	@SuppressWarnings("unused")
	public ResponseEntity<String>  cadastrar(@RequestBody Candidato cand) {
		
		Candidato cd=new Candidato();
		//Vaga vg=vr.findByCodigo(codigo);
		//try {
			//if(vg==null) {
		//}else {
				//vr.findByCodigo(codigo);
				cd.setId(cand.getId());
				cd.setRg(cand.getRg());
				cd.setNomeCandidato(cand.getNomeCandidato());
				cd.setEmail(cand.getEmail());
				cd.setVaga(cand.getVaga());
				//Vaga v=vr.save(cd);
//				v.setCodigo(vg.getCodigo());
//				v.setNome(vg.getNome());
//				v.setDescricao(vg.getDescricao());
//				v.setData(vg.getData());
//				v.setSalario(vg.getSalario());
			
				cr.save(cd);
//			//cr.save(cd);
//		} catch (Exception e) {
//			e.getMessage();
//			e.printStackTrace();
//		}
		
		
//		Vaga vg=vr.findByCodigo(codigo);
//		if(vg==null) {
//			return  "VAGA não existe";
////			return ResponseEntity.noContent().build().ok("ID não existe");
////			return "redirect:/{codigo}";
//		}
//
//		// rg duplicado
//		if (cr.findByRg(cand.getRg()) != null) {
//			return "RG duplicado";
////			return "redirect:/{codigo}";
//		}
//
//		Vaga vaga = vr.findByCodigo(codigo);
//		//cand.setVaga(vaga);
	
		//return "redirect:/{codigo}";
		
		//Candidato vg=cr.save(cd);
		return ResponseEntity.ok().body("Candidato Cadastrado");
	}

	public java.util.List<Candidato> buscarPorNome(@PathVariable String nome) {
		Candidato cand=new Candidato();
		java.util.List<Candidato> cd=cr.findByNomeCandidato(nome);
		java.util.List<Candidato> candidatos=new ArrayList<>();

		for(Candidato cad:cd) {
			cand.setId(cad.getId());
			cand.setNomeCandidato(cad.getNomeCandidato());
			cand.setRg(cad.getRg());
			cand.setEmail(cad.getEmail());
			cand.setVaga(cad.getVaga());		}
		candidatos.add(cand);
		return cd;
	}

	public Candidato buscaPorRg(@PathVariable("rg") String rg) {
		Vaga vagas=new Vaga();
		Candidato cand = cr.findByRg(rg);
		Iterable<Vaga> vaga = vr.findByNome(rg);
		return cand;

	}
	public java.util.List<Candidato> listCand() {
		java.util.List<Candidato> cand=cr.findAll();
		return cand;

	}
	public String delete(@PathVariable String rg) {
		Candidato candidato = cr.findByRg(rg);
		Vaga vaga = candidato.getVaga();
		String codigo = "" + vaga.getCodigo();

		cr.delete(candidato);
		return "redirect:/ "+codigo;

	}
//	public Candidato buscarPorId(@PathVariable long codigo) {
//		return vr.findByCodigo(codigo);
//
//	}
//	
	

}
