CREATE OR REPLACE FUNCTION getTopActors(genreNameInput VARCHAR)
RETURNS TABLE(ActorName VARCHAR, NumMovies INT, DebutYear INT, DebutFilm VARCHAR, DirectorName VARCHAR) AS $$
BEGIN
    RETURN QUERY
    WITH actor_movies AS (
        -- Obtiene una lista única de películas por actor en el género especificado
        SELECT DISTINCT
            a.actorid,
            a.actorname AS actor_name,
            m.movieid,
            CAST(m.year AS INTEGER) AS movie_year, 
            m.movietitle AS movie_title
        FROM 
            imdb_actors a
            JOIN imdb_actormovies am ON a.actorid = am.actorid
            JOIN imdb_movies m ON am.movieid = m.movieid
            JOIN imdb_moviegenres mg ON m.movieid = mg.movieid
            JOIN genres g ON mg.genreid = g.genreid
        WHERE 
            g.genrename = genreNameInput
    ),
    actor_movie_counts AS (
        -- Cuenta el número de películas únicas por actor
        SELECT
            actorid,
            actor_name,
            CAST(COUNT(movieid) AS INTEGER) AS num_movies
        FROM 
            actor_movies
        GROUP BY 
            actorid, actor_name
    ),
    actor_debut_films AS (
        -- Determina la película de debut para cada actor
        SELECT
            am.actorid,
            am.actor_name,
            MIN(am.movie_year) AS debut_year
        FROM 
            actor_movies am
        INNER JOIN actor_movie_counts amc ON am.actorid = amc.actorid
        WHERE 
            amc.num_movies > 4
        GROUP BY 
            am.actorid, am.actor_name
    ),
    actor_debut_details AS (
        -- Detalles de las películas de debut, incluyendo directores
        SELECT
            amd.actorid,
            amd.actor_name,
            amd.movie_year,
            amd.movie_title,
            d.directorname AS director_name,
            adf.debut_year,
            amc.num_movies
        FROM 
            actor_movies amd
            JOIN actor_debut_films adf ON amd.actorid = adf.actorid AND amd.movie_year = adf.debut_year
            JOIN actor_movie_counts amc ON amd.actorid = amc.actorid
            JOIN imdb_directormovies dm ON amd.movieid = dm.movieid
            JOIN imdb_directors d ON dm.directorid = d.directorid
    )
    SELECT 
        actor_name AS ActorName,
        num_movies AS NumMovies, 
        debut_year AS DebutYear, 
        movie_title AS DebutFilm, 
        director_name AS DirectorName
    FROM 
        actor_debut_details
    ORDER BY 
        num_movies DESC, debut_year, movie_title, director_name;
END;
$$ LANGUAGE plpgsql;

-- QUERY DE PRUEBA
SELECT ia.actorname,
       ia2.actorid,
       Count(ia2.actorid)
FROM   imdb_actors ia
       JOIN imdb_actormovies ia2
         ON ia.actorid = ia2.actorid
       JOIN imdb_movies im
         ON ia2.movieid = im.movieid
       JOIN imdb_moviegenres im2
         ON im.movieid = im2.movieid
       JOIN genres g
         ON im2.genreid = g.genreid
WHERE  g.genrename = 'Drama'
       AND ia.actorname = 'Jackson, Samuel L.'
GROUP  BY ia2.actorid,
          ia.actorname
ORDER  BY Count(ia2.actorid) DESC  
