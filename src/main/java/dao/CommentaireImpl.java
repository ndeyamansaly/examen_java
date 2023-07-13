package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import config.HibernateUtil;
import entities.Commentaire;

import java.util.List;

public class CommentaireImpl implements ICommentaire{


    private Session session;

    private Transaction transaction;

    public CommentaireImpl(){
        session = HibernateUtil.getSessionFactory().openSession();
    }
    @Override
    public int create(Commentaire commentaire) {
        int ok = 0;
        try {
            transaction = session.beginTransaction();
            session.save(commentaire);
            transaction.commit();
            ok = 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<Commentaire> getAll() {
        return session.createCriteria(Commentaire.class).list();
    }

    @Override
    public Commentaire get(int id) {
        return(session.get(Commentaire.class,id));
    }

    @Override
    public int update(Commentaire commentaire) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

}
