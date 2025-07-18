UPDATE orderdetail od
SET
-- Calcula y establece el nuevo precio, disminuyendo un 2% por cada año desde 2022 hasta la fecha en la que se ordena el pedido
price = Round(Cast(Power(0.98, ( 2022 - Extract(year FROM o.orderdate) )) *
                   p.price AS
                           NUMERIC
              ), 2)
FROM   products p,
       orders o
WHERE  p.prod_id = od.prod_id
       AND od.orderid = o.orderid;

-- QUERY DE PRUEBA
SELECT od.orderid,
       o.orderdate,
       p.price  AS actual_price,
       od.price AS order_price
FROM   orders o
       JOIN orderdetail od
         ON o.orderid = od.orderid
       JOIN products p
         ON od.prod_id = p.prod_id  