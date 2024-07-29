package com.AppRH.AppRH.services;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;
import com.AppRH.AppRH.repositories.CandidatoRepository;
import com.AppRH.AppRH.repositories.VagaRepository;

import jakarta.validation.Valid;

@Service
public class VagaService {

	@Autowired
	private VagaRepository vr;

	@Autowired
	private CandidatoRepository cr;

	//Cadastrar Vaga
	private String form() {

		return null;

	}
	
	public Vaga cadastrar(@RequestBody Vaga vaga) {
		return vr.save(vaga);
	}

	public java.util.List<Vaga> buscarPorNome(@PathVariable String nome) {
		Vaga vagas=new Vaga();
		java.util.List<Vaga> vg=vr.findByNome(nome);
		java.util.List<Vaga> vag=new ArrayList<>();

		for(Vaga vgv:vg) {
			vagas.setCodigo(vgv.getCodigo());
			vagas.setNome(vgv.getNome());
			vagas.setDescricao(vgv.getDescricao());
			vagas.setData(vgv.getData());
			vagas.setSalario(vgv.getSalario());
			vagas.setCandidatos(vgv.getCandidatos());
		}
		vag.add(vagas);
		return vg;
	}

	public Vaga detalhesVaga(@PathVariable("codigo") long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		Iterable<Candidato> canditados = cr.findByVaga(vaga);
		return vaga;

	}
	public java.util.List<Vaga> listTodos() {
		java.util.List<Vaga> vg=vr.findAll();
		return vg;

	}
	public ResponseEntity<Vaga> delete(@PathVariable long codigo) {
		vr.deleteById(codigo);
		return ResponseEntity.noContent().build();

	}
	public Vaga buscarPorId(@PathVariable long codigo) {
		return vr.findByCodigo(codigo);

	}
}
