package dao;

import entities.Membre;

public interface IMembre extends Repository<Membre>{
    public Membre seConnecter(String username);
}
