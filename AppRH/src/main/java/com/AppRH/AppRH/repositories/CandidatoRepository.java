package com.AppRH.AppRH.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;

public interface CandidatoRepository extends JpaRepository<Candidato, Long>{
	
Iterable<Candidato>findByVaga(Vaga vaga);
	
	Candidato findByRg(String rg);
	
	Candidato findById(long id);
	
	List<Candidato>findByNomeCandidato(String nomeCandidato);

}
