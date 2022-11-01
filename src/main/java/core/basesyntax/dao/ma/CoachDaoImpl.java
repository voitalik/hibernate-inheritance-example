package core.basesyntax.dao.ma;

import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.ma.Coach;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CoachDaoImpl extends PersonDaoImpl implements CoachDao {
    public CoachDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Coach> findByExperienceGreaterThan(int years) {
        try (Session session = sessionFactory.openSession()) {
            Query<Coach> query = session.createQuery("from Coach c"
                    + " WHERE c.experience > :years", Coach.class);
            query.setParameter("years", years);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get coaches with experiance more than: " + years, e);
        }
    }
}
