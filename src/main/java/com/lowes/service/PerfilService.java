package com.lowes.service;

import java.util.List;

import com.lowes.entity.Perfil;

public interface PerfilService {
	public Integer createPerfil(Perfil perfil);
    public Perfil updatePerfil(Perfil perfil);
    public void deletePerfil(Integer idPerfil);
    public List<Perfil> getAllPerfiles();
    public Perfil getPerfil(Integer idPerfil);
}
