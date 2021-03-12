package com.lowes.dao;

import java.util.List;

import com.lowes.entity.Perfil;

public interface PerfilDAO {
	public Integer createPerfil(Perfil perfil);
    public Perfil updatePerfil(Perfil perfil);
    public void deletePerfil(Integer idPerfil);
    public List<Perfil> getAllPerfiles();
    public Perfil getPerfil(Integer idPerfil);
}
