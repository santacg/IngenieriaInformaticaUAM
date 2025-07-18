CREATE OR REPLACE PROCEDURE setOrderAmount() 
LANGUAGE plpgsql 
AS $$
BEGIN
    -- Calcula las cantidades netas de cada pedido
    WITH calculated_amounts AS (
        SELECT 
            orderid, 
            -- Calcula la cantidad neta sumando el producto de precio y cantidad para cada detalle de pedido
            SUM(price * quantity) AS net_amount
        FROM 
            orderdetail
        GROUP BY 
            orderid
    )
    UPDATE orders
    SET 
        -- Establece netamount solo si es NULL, de lo contrario, mantiene el valor existente
        netamount = CASE 
                        WHEN orders.netamount IS NULL THEN ca.net_amount 
                        ELSE orders.netamount 
                    END,
        -- Establece totalamount solo si es NULL, de lo contrario, mantiene el valor existente
        totalamount = CASE 
                          WHEN orders.totalamount IS NULL THEN ca.net_amount + orders.tax 
                          ELSE orders.totalamount 
                      END
    FROM calculated_amounts ca
    WHERE 
        -- Asegura que la actualización se aplique solo a los pedidos correspondientes
        orders.orderid = ca.orderid 
        -- Filtra para actualizar solo los pedidos que tienen netamount o totalamount NULL
        AND (orders.netamount IS NULL OR orders.totalamount IS NULL);
END;
$$;
