CREATE OR REPLACE FUNCTION getTopSales(year1 INT, year2 INT)
RETURNS TABLE(Year INT, Film VARCHAR, Sales BIGINT) AS $$
BEGIN
    RETURN QUERY
        -- Calcula las ventas totales por película por año
        WITH YearSalesPerMovie AS (
            SELECT
                -- Extrae el año de la fecha del pedido y lo convierte a entero
                CAST(date_part('year', o.orderdate) AS INTEGER) AS year_sales, 
                im.movietitle AS moviename, 
                -- Suma la cantidad de ventas por película
                SUM(od.quantity) AS total_quantity
            FROM 
                orders o
                JOIN orderdetail od ON o.orderid = od.orderid 
                JOIN products p ON od.prod_id = p.prod_id 
                JOIN imdb_movies im ON im.movieid = p.movieid
            WHERE  
                -- Filtra los pedidos entre los años especificados
                date_part('year', o.orderdate) BETWEEN year1 AND year2
            GROUP BY 
                p.movieid, year_sales, moviename
        ), RankByYearSales AS (
            SELECT
                year_sales,
                moviename,
                total_quantity,
                -- Asigna un rango a las ventas por año, ordenadas de mayor a menor
                RANK() OVER (PARTITION BY year_sales ORDER BY total_quantity DESC) AS sales_rank
            FROM YearSalesPerMovie 
        )
        -- Selecciona las películas con el mayor número de ventas por año
        SELECT
            year_sales,
            moviename,
            total_quantity
        FROM RankByYearSales
        WHERE sales_rank = 1 -- Filtra para obtener solo las películas con el rango más alto (más vendidas) por año
        ORDER BY total_quantity DESC;
END;
$$ LANGUAGE plpgsql;


-- QUERY DE PRUEBA
SELECT Date_part('year', o.orderdate) AS year_sales,
       p.movieid,
       Count(o2.quantity)             AS total_quantity
FROM   orders o
       JOIN orderdetail o2
         ON o.orderid = o2.orderid
       JOIN products p
         ON o2.prod_id = p.prod_id
WHERE  Date_part('year', o.orderdate) = 2019
GROUP  BY p.movieid,
          year_sales
ORDER  BY total_quantity DESC  