package com.AppRH.AppRH.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, Long>{
	
	Vaga findByCodigo(long codigo);
	List<Vaga> findByNome(String nome);
	

	Vaga save(Candidato cd);

}
