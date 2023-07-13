package dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import config.HibernateUtil;
import entities.Commentaire;
import entities.Membre;

import javax.persistence.EntityManager;
import java.util.List;


public class MembreImpl implements dao.IMembre {

    private Session session;

    private Transaction transaction;
    public MembreImpl(){
        session = HibernateUtil.getSessionFactory().openSession();
    }
    @Override
    public int create(Membre membre) {
        int ok = 0;
        try {
            transaction = session.beginTransaction();
            session.save(membre);
            transaction.commit();
            ok = 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<Membre> getAll() {
        return session.createCriteria(Membre.class).list();
    }

    @Override
    public Membre get(int id) {
        return(session.get(Membre.class,id));
    }

    @Override
    public int update(Membre membre) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }


    @Override
    public Membre seConnecter(String username) {
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query<Membre> query = session.createQuery("SELECT m FROM Membre m WHERE m.username = :username", Membre.class);
            query.setParameter("username", username);
            Membre membre = query.uniqueResult();
            transaction.commit();
            return membre;
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
