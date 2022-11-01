package core.basesyntax.dao.animal;

import core.basesyntax.dao.AbstractDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.zoo.Animal;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AnimalDaoImpl extends AbstractDao implements AnimalDao {
    public AnimalDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Animal save(Animal animal) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(animal);
            transaction.commit();
            return animal;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert animal: " + animal, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Animal> findByNameFirstLetter(Character character) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Animal> cr = cb.createQuery(Animal.class);
            Root<Animal> root = cr.from(Animal.class);
            Predicate like = cb.like(cb.lower(root.get("name")),
                    character.toString().toLowerCase() + "%");
            cr.where(like);
            return session.createQuery(cr).getResultList();
            // Query<Animal> getAllAnimals = session.createQuery("from Animal a"
            //      + " WHERE LOWER(a.name) LIKE LOWER(:c)", Animal.class);
            // getAllAnimals.setParameter("c", character + "%");
            // return getAllAnimals.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get animals by first letter: " + character, e);
        }
    }
}
