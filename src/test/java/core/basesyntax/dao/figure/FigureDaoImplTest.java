package core.basesyntax.dao.figure;

import core.basesyntax.dao.AbstractTest;
import core.basesyntax.model.figure.Circle;
import core.basesyntax.model.figure.Figure;
import core.basesyntax.model.figure.Triangle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class FigureDaoImplTest extends AbstractTest {
    public static final String PINK_COLOR = "Pink";
    public static final String RED_COLOR = "Red";
    private FigureDao<Figure> figureDao;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {
                Circle.class,
                Triangle.class
        };
    }

    @Before
    public void setUp() {
        figureDao = new FigureDaoImpl<>(getSessionFactory());
    }

    @Test
    public void createCircle_Ok() {
        Circle circle = new Circle();
        circle.setColor(PINK_COLOR);
        circle.setRadius(5);
        Figure actualCircle = figureDao.save(circle);
        Assert.assertNotNull(actualCircle);
        Assert.assertEquals(1L, actualCircle.getId().longValue());
    }

    @Test
    public void createTriangle_Ok() {
        Triangle triangle = new Triangle();
        triangle.setColor(RED_COLOR);
        triangle.setArea(10);
        Figure actualTriangle = figureDao.save(triangle);
        Assert.assertNotNull(actualTriangle);
        Assert.assertEquals(1L, actualTriangle.getId().longValue());
    }

    @Test
    public void findByColor_Ok() {
        FigureDao<Triangle> triangleFigureDao = new FigureDaoImpl<>(getSessionFactory());
        Triangle redTriangle = new Triangle();
        redTriangle.setColor(RED_COLOR);
        Triangle pinkTriangle = new Triangle();
        pinkTriangle.setColor(PINK_COLOR);
        Triangle secondPinkTriangle = new Triangle();
        secondPinkTriangle.setColor(PINK_COLOR);

        figureDao.save(redTriangle);
        figureDao.save(pinkTriangle);
        figureDao.save(secondPinkTriangle);
        List<Triangle> pinkTriangles = triangleFigureDao.findByColor(PINK_COLOR, Triangle.class);
        Assert.assertNotNull(pinkTriangle);
        Assert.assertEquals(2, pinkTriangles.size());
    }
}
