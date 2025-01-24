package org.example;

import org.example.model.Director;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {

        Configuration cfg = new Configuration().addAnnotatedClass(Director.class)
                .addAnnotatedClass(Movie.class);
        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();

            //Получение режиссера с id=1 и вывод его фильмов
//            Director director = session.get(Director.class, 1);
//            director.getMovies().forEach(System.out::println);

            //Создание фильма и назначение его режиссеру с id=1
//            Movie movie = new Movie(director, "MovieTest1", 2011);
//            director.getMovies().add(movie);
//            session.save(movie);

            //Создание нового режиссера и фильма
//            Director newDirector = new Director("TestDirector", 22);
//            Movie newMovie = new Movie(newDirector, "MovieTest2",2024);
//            newDirector.setMovies(new ArrayList<>(Collections.singletonList(newMovie)));
//            session.save(newDirector);
//            session.save(newMovie);

            //Сменить режиссера у фильма
//            Director director = session.get(Director.class, 3);
//            Movie movie = session.createQuery("from Movie where name LIKE '%Test%'", Movie.class)
//                    .stream().findFirst().orElse(null);
//            movie.setDirector(director);
//            director.getMovies().add(movie);

            //Удалить фильмы у режиссера
            Director director = session.get(Director.class, 3);
            List<Movie> movies = director.getMovies();
            for (Movie movie : movies) {
                session.remove(movie);
            }
            director.getMovies().clear();
            session.getTransaction().commit();
        } finally {
            sf.close();
        }
    }
}
